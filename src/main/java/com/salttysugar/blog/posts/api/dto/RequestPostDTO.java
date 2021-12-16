package com.salttysugar.blog.posts.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ApiModel("Create/Update post request")
public final class RequestPostDTO {
    @NotEmpty(message = "field \"title\" is required and cannot be blank")
    String title;
    String content;
    String slug;

    @Builder.Default
    Map<String, Object> meta = new HashMap<>();
}
