package by.shestakov.ridesservice.feign;

import by.shestakov.ridesservice.dto.response.RoutingResponse;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "routing-service", url = "https://graphhopper.com/api/1")
public interface RoutingFeign {
    @GetMapping("/route")
    RoutingResponse requestDistance(@RequestParam("point") List<String> points,
                                    @RequestParam("calc_points") Boolean calc,
                                    @RequestParam("key") String key);
}
