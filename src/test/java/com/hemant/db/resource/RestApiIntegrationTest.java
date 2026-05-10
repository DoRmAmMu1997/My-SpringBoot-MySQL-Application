package com.hemant.db.resource;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.hemant.db.model.Address;
import com.hemant.db.model.Department;
import com.hemant.db.model.Employee;
import com.hemant.db.model.Profile;
import com.hemant.db.repository.AddressRepository;
import com.hemant.db.repository.DepartmentRepository;
import com.hemant.db.repository.EmployeeRepository;
import com.hemant.db.repository.ProfileRepository;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class RestApiIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @BeforeEach
    void cleanDatabase() {
        addressRepository.deleteAll();
        profileRepository.deleteAll();
        employeeRepository.deleteAll();
        departmentRepository.deleteAll();
    }

    @Test
    void employeeCrudAndValidationErrorsUseSafeResponses() throws Exception {
        mockMvc.perform(post("/rest/Employee/insert")
                .contentType(MediaType.APPLICATION_JSON)
                .content(employeeJson("employee@example.com")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("employee@example.com"))
                .andExpect(jsonPath("$.password").doesNotExist());

        Employee employee = employeeRepository.findByEmail("employee@example.com").orElseThrow();

        mockMvc.perform(get("/rest/Employee/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].email").value("employee@example.com"))
                .andExpect(jsonPath("$[0].password").doesNotExist());

        mockMvc.perform(get("/rest/Employee/findbyname").param("name", "Test Employee"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].designation").value("Developer"));

        mockMvc.perform(post("/rest/Employee/updatedesignation")
                .param("Id", employee.getId().toString())
                .param("designation", "Manager"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.designation").value("Manager"))
                .andExpect(jsonPath("$.password").doesNotExist());

        mockMvc.perform(delete("/rest/Employee/delete").param("Id", employee.getId().toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountStatus").value("Deactivated"));

        mockMvc.perform(post("/rest/Employee/updatemobile")
                .param("Id", "999")
                .param("mobile", "9876543210"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", containsString("Employee not found with id 999")));

        mockMvc.perform(post("/rest/Employee/insert")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"Invalid\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation failed"))
                .andExpect(jsonPath("$.details.email").exists());
    }

    @Test
    void departmentAddressAndProfileFlowsWorkWithH2() throws Exception {
        mockMvc.perform(post("/rest/Department/insert")
                .contentType(MediaType.APPLICATION_JSON)
                .content(departmentJson()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Engineering"));
        Department department = departmentRepository.findByName("Engineering").get(0);

        mockMvc.perform(post("/rest/Address/insert")
                .contentType(MediaType.APPLICATION_JSON)
                .content(addressJson(1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].city").value("Bangalore"));
        Address address = addressRepository.findByCity("Bangalore").get(0);

        mockMvc.perform(post("/rest/Profile/insert")
                .contentType(MediaType.APPLICATION_JSON)
                .content(profileJson(1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gender").value("Male"));
        Profile profile = profileRepository.findByGender("Male").get(0);

        mockMvc.perform(get("/rest/Department/findbyname").param("name", "Engineering"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].address").value("Bangalore"));

        mockMvc.perform(post("/rest/Department/updatefloor")
                .param("Id", department.getId().toString())
                .param("floor", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.floor").value(5));

        mockMvc.perform(get("/rest/Address/findbycity").param("city", "Bangalore"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].pin").value(560001));

        mockMvc.perform(post("/rest/Address/updateaddress")
                .param("Id", address.getId().toString())
                .param("address1", "Line 1")
                .param("address2", "Line 2")
                .param("city", "Mysore")
                .param("state", "Karnataka")
                .param("pin", "570001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.city").value("Mysore"));

        mockMvc.perform(get("/rest/Profile/findbygender").param("gender", "Male"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].hobbies").value("Reading"));

        mockMvc.perform(post("/rest/Profile/updatehobbies")
                .param("Id", profile.getId().toString())
                .param("hobbies", "Chess"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.hobbies").value("Chess"));

        mockMvc.perform(delete("/rest/Profile/delete").param("Id", profile.getId().toString()))
                .andExpect(status().isOk());
    }

    @Test
    void loginAndLogoutReportExpectedStatusCodes() throws Exception {
        employeeRepository.save(sampleEmployee("login@example.com"));

        mockMvc.perform(post("/rest/Login/login")
                .param("email", "login@example.com")
                .param("password", "wrong"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(false))
                .andExpect(jsonPath("$.employee").doesNotExist());

        mockMvc.perform(post("/rest/Login/login")
                .param("email", "login@example.com")
                .param("password", "secret"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(true))
                .andExpect(jsonPath("$.employee.email").value("login@example.com"))
                .andExpect(jsonPath("$.employee.password").doesNotExist());

        mockMvc.perform(post("/rest/Login/login")
                .param("email", "login@example.com")
                .param("password", "secret"))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.message", containsString("already logged in")));

        mockMvc.perform(post("/rest/Login/logout").param("email", "login@example.com"))
                .andExpect(status().isOk())
                .andExpect(content().string("Logged Out"));

        mockMvc.perform(get("/rest/Login/findbyemail").param("email", "login@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("login@example.com"))
                .andExpect(jsonPath("$.password").doesNotExist());
    }

    private Employee sampleEmployee(String email) {
        Employee employee = new Employee();
        employee.setName("Test Employee");
        employee.setDesignation("Developer");
        employee.setSalary(50000);
        employee.setDepId(1);
        employee.setMobile(9876543210L);
        employee.setEmail(email);
        employee.setPassword("secret");
        employee.setStatus("Logged Out");
        employee.setAccountStatus("Activated");
        employee.setCreatedBy("test");
        employee.setCreatedAt("2026-05-10");
        return employee;
    }

    private String employeeJson(String email) {
        return "{"
                + "\"name\":\"Test Employee\","
                + "\"designation\":\"Developer\","
                + "\"salary\":50000,"
                + "\"depId\":1,"
                + "\"mobile\":9876543210,"
                + "\"email\":\"" + email + "\","
                + "\"password\":\"secret\","
                + "\"status\":\"Logged Out\","
                + "\"accountStatus\":\"Activated\","
                + "\"createdBy\":\"test\","
                + "\"createdAt\":\"2026-05-10\""
                + "}";
    }

    private Department sampleDepartment() {
        Department department = new Department();
        department.setName("Engineering");
        department.setAddress("Bangalore");
        department.setFloor(3);
        department.setCreatedBy("test");
        department.setCreatedAt("2026-05-10");
        return department;
    }

    private String departmentJson() {
        return "{"
                + "\"name\":\"Engineering\","
                + "\"address\":\"Bangalore\","
                + "\"floor\":3,"
                + "\"createdBy\":\"test\","
                + "\"createdAt\":\"2026-05-10\""
                + "}";
    }

    private Address sampleAddress(Integer empId) {
        Address address = new Address();
        address.setAddress1("Street 1");
        address.setAddress2("Area 1");
        address.setCity("Bangalore");
        address.setState("Karnataka");
        address.setPIN(560001);
        address.setEmpId(empId);
        address.setCreatedBy("test");
        address.setCreatedAt("2026-05-10");
        return address;
    }

    private String addressJson(Integer empId) {
        return "{"
                + "\"address1\":\"Street 1\","
                + "\"address2\":\"Area 1\","
                + "\"city\":\"Bangalore\","
                + "\"state\":\"Karnataka\","
                + "\"pin\":560001,"
                + "\"empId\":" + empId + ","
                + "\"createdBy\":\"test\","
                + "\"createdAt\":\"2026-05-10\""
                + "}";
    }

    private Profile sampleProfile(Integer empId) {
        Profile profile = new Profile();
        profile.setGender("Male");
        profile.setDOB("2000-01-01");
        profile.setHobbies("Reading");
        profile.setEmpId(empId);
        profile.setCreatedBy("test");
        profile.setCreatedAt("2026-05-10");
        return profile;
    }

    private String profileJson(Integer empId) {
        return "{"
                + "\"gender\":\"Male\","
                + "\"dob\":\"2000-01-01\","
                + "\"hobbies\":\"Reading\","
                + "\"empId\":" + empId + ","
                + "\"createdBy\":\"test\","
                + "\"createdAt\":\"2026-05-10\""
                + "}";
    }
}
