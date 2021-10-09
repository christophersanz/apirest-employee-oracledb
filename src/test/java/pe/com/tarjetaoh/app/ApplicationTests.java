package pe.com.tarjetaoh.app;

import oracle.jdbc.pool.OracleConnectionPoolDataSource;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import pe.com.tarjetaoh.app.controller.EmployeeController;
import pe.com.tarjetaoh.app.model.Employee;
import pe.com.tarjetaoh.app.repository.EmployeeRepository;
import pe.com.tarjetaoh.app.util.FileUtil;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.math.BigDecimal;
import java.net.URI;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT,classes=Application.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class ApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private RestTemplate restTemplate;

    private MockRestServiceServer mockServer;

    @Mock
    private EmployeeRepository employeeRepository;

    private EmployeeController employeeController;

    private ObjectMapper mapper = new ObjectMapper();

    @Before
    public void init() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @BeforeClass
    public static void setDataSource() {
        try {
            // Create initial context
            System.setProperty(Context.INITIAL_CONTEXT_FACTORY,
                    "org.apache.naming.java.javaURLContextFactory");
            System.setProperty(Context.URL_PKG_PREFIXES, "org.apache.naming");
            InitialContext ic = new InitialContext();
            ic.createSubcontext("jdbc");

            // Construct DataSource
            OracleConnectionPoolDataSource ds = new OracleConnectionPoolDataSource();
            ds.setURL("jdbc:oracle:thin:@server:port:SID");
            ds.setUser("user");
            ds.setPassword("password");

            ic.bind("jdbc/ds", ds);
        } catch (Exception ex) {

        }
    }



    @Test
    public void testGetMappingAllEmployees1() throws Exception {
        String pathBase = "/api/employees";

        List<Employee> employees = new ArrayList<>();

        mockServer.expect(requestTo(new URI(pathBase)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(employees)));

        List<Employee> employeesMock = employeeRepository.findAll();
        Assert.assertEquals(employeesMock, employees);
    }

    @Test
    public void testGetMappingAllEmployees2() throws Exception {
        String pathBase = "/api/employees";

        List<Employee> employees = new ArrayList<>();

        mockServer.expect(requestTo(new URI(pathBase)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(employees)));

        HttpHeaders httpHeaders = new HttpHeaders();

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .get(pathBase)
                .headers(httpHeaders)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        );

        resultActions.andExpect( status().isOk() );
    }

    @Test
    public void testPostMappingAddEmployee() throws Exception {
        String pathBase = "/api/employee";

        Employee employee = new Employee();

        mockServer.expect(requestTo(new URI(pathBase)))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(employee)));

        HttpHeaders httpHeaders = new HttpHeaders();

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders
                .post(pathBase)
                .headers(httpHeaders)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(mapper.writeValueAsString(employee)) );

        resultActions.andExpect( status().isOk() );
    }


}
