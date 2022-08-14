package learn.springboot_rest.controller;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import learn.springboot_rest.repository.SomeBean;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FilteringController {

    @GetMapping("/static-filter")
    private SomeBean retrieveSomeBean() {
        return new SomeBean(
                "203977V",
                "KingKong",
                "somePassword",
                "running",
                "cake",
                20
        );
    }

    @GetMapping("/dynamic-filter")
    private MappingJacksonValue retrieveSomeFilteredBean() {
        SomeBean bean = new SomeBean(
                "203977V",
                "KingKong",
                "somePassword",
                "running",
                "cake",
                20
        );

        // Now I have to create filters
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "food");

        // NOTE: I have to define this `id` (a.k.a "SomeBeanFilter") onto actual bean
        // see `SomeBean` class <- Approach 3
        FilterProvider filters = new SimpleFilterProvider().addFilter("SomeBeanFilter", filter);

        // Map those filters to JSON
        MappingJacksonValue mapping = new MappingJacksonValue(bean);
        mapping.setFilters(filters);

        return mapping;
    }
}
