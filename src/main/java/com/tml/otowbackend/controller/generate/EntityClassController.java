package com.tml.otowbackend.controller.generate;

import com.tml.otowbackend.core.anno.TokenRequire;
import com.tml.otowbackend.engine.ai.result.EntityClassDefinition;
import com.tml.otowbackend.engine.tree.common.R;
import com.tml.otowbackend.service.EntityClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 描述: 实体类管理控制器
 * 负责实体类的增删改查功能
 */
@RequiredArgsConstructor
@RequestMapping("/otow/entity")
@RestController
@Validated
public class EntityClassController {

    private final EntityClassService entityClassService;

    @PostMapping("/add")
    @TokenRequire
    public R<Void> addEntityClass(@RequestParam Long projectId, @RequestBody EntityClassDefinition entity) {
        return entityClassService.addEntityClass(projectId, entity);
    }

    @PutMapping("/update")
    @TokenRequire
    public R<Void> updateEntityClass(@RequestParam Long projectId, @RequestParam String className, @RequestBody EntityClassDefinition entity) {
        return entityClassService.updateEntityClass(projectId, className, entity);
    }

    @DeleteMapping("/delete")
    @TokenRequire
    public R<Void> deleteEntityClass(@RequestParam Long projectId, @RequestParam String className) {
        return entityClassService.deleteEntityClass(projectId, className);
    }

    @GetMapping("/list")
    @TokenRequire
    public R<List<EntityClassDefinition>> getEntityClasses(@RequestParam Long projectId) {
        return entityClassService.getEntityClasses(projectId);
    }
}