package com.tuannq.store.controller.admin;

import com.tuannq.store.entity.Users;
import com.tuannq.store.exception.ArgumentException;
import com.tuannq.store.exception.BadRequestException;
import com.tuannq.store.exception.NotFoundException;
import com.tuannq.store.model.dto.UserDTO;
import com.tuannq.store.model.request.UserFormAdmin;
import com.tuannq.store.model.response.SuccessResponse;
import com.tuannq.store.security.CustomUserDetails;
import com.tuannq.store.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller()
public class ManageUserController {
    private final UserService userService;
    private final MessageSource messageSource;

    @Autowired
    public ManageUserController(
            UserService userService,
            MessageSource messageSource
    ) {
        this.userService = userService;
        this.messageSource = messageSource;
    }

    @GetMapping("/admin/users")
    public String getAll(
            Model model,
            @RequestParam(value = "search", required = false) String keyword,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size
    ) {
        int offset = Optional.of(page).map(p -> {
            if (p > 0) return p - 1;
            else return 0;
        }).orElse(0);

        Pageable pageable = PageRequest.of(offset, size, Sort.by("id").ascending());
        Page<Users> users = userService.search(keyword, pageable);
        model.addAttribute("users", users);
        return "admin/user/list";
    }

    @GetMapping("/api/admin/users/{id}")
    public ResponseEntity<SuccessResponse<UserDTO>> getUser(
            @PathVariable long id
    ) throws ArgumentException {

        var userOpt = userService.findById(id);
        if (userOpt.isEmpty())
            throw new NotFoundException(messageSource.getMessage("not-found.user.id", null, LocaleContextHolder.getLocale()).concat(String.valueOf(id)));

        return ResponseEntity.ok(new SuccessResponse<>(
                null,
                new UserDTO(userOpt.get())
        ));
    }

    @PostMapping("/api/admin/users")
    public ResponseEntity<SuccessResponse<UserDTO>> addUser(
            @Validated @RequestBody UserFormAdmin form
    ) throws ArgumentException {
        Users user = userService.addUserByAdmin(form);
        return ResponseEntity.ok(new SuccessResponse<>(
                messageSource.getMessage("add.success", null, LocaleContextHolder.getLocale()),
                new UserDTO(user)
        ));
    }

    @PutMapping("/api/admin/users/{id}")
    public ResponseEntity<SuccessResponse<UserDTO>> updateUser(
            @Validated @RequestBody UserFormAdmin form,
            @PathVariable long id
    ) throws ArgumentException {

        var userOpt = userService.findById(id);
        if (userOpt.isEmpty())
            throw new NotFoundException(messageSource.getMessage("not-found.user.id", null, LocaleContextHolder.getLocale()).concat(String.valueOf(id)));
        var identity = ((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();

        if (identity.getId() == id && form.getIsDeleted())
            throw new BadRequestException(messageSource.getMessage("update.fail", null, LocaleContextHolder.getLocale()));

        Users user = userService.editUserByAdmin(userOpt.get(), form);

        if (identity.getId().equals(user.getId())) {
            UserDetails principal = new CustomUserDetails(user);
            Authentication authentication = new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        return ResponseEntity.ok(new SuccessResponse<>(
                messageSource.getMessage("update.success", null, LocaleContextHolder.getLocale()),
                new UserDTO(user)
        ));
    }

    @DeleteMapping("/api/admin/users/{id}")
    public ResponseEntity<?> deleteUser(
            @PathVariable long id
    ) throws Exception {
        if (((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser().getId() == id)
            return ResponseEntity.badRequest().body(new SuccessResponse<>(messageSource.getMessage("delete.fail", null, LocaleContextHolder.getLocale()), null));

        userService.deleteById(id);

        return ResponseEntity.ok(new SuccessResponse<>(messageSource.getMessage("delete.success", null, LocaleContextHolder.getLocale()), null));
    }

}
