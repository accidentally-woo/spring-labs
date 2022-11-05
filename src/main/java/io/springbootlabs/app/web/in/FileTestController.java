package io.springbootlabs.app.web.in;

import io.springbootlabs.domain.file.service.TestInmemoryFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/file-test")
@RequiredArgsConstructor
public class FileTestController {
    private final TestInmemoryFileService testInmemoryFileService;

    /**
     * https://zetcode.com/springboot/serveimage/
     * @param httpServletResponse
     */
    @GetMapping("/resource/httpservlet-response")
    public void resource(HttpServletResponse httpServletResponse) {
        testInmemoryFileService.resource(httpServletResponse);
    }

    /**
     * https://zetcode.com/springboot/serveimage/
     * @return
     */
    @GetMapping("/resource/response-entity")
    @ResponseBody
    public ResponseEntity<byte[]> resourceResponseEntity() {
        return testInmemoryFileService.resourceResponseEntity();
    }

    /**
     * https://zetcode.com/springboot/serveimage/
     */
    @GetMapping("/resource/response-entity-inputstream")
    @ResponseBody
    public ResponseEntity<InputStreamResource> resourceResponseEntityAndInputStreamResource() {
        return testInmemoryFileService.resourceResponseEntityAndInputStreamResource();
    }
}
