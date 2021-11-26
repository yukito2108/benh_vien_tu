package com.tuannq.store.service.impl;

import com.tuannq.store.entity.OTP;
import com.tuannq.store.entity.Role;
import com.tuannq.store.entity.Users;
import com.tuannq.store.exception.ArgumentException;
import com.tuannq.store.exception.NotFoundException;
import com.tuannq.store.model.request.*;
import com.tuannq.store.model.type.TransactionType;
import com.tuannq.store.repository.OTPRepository;
import com.tuannq.store.repository.RoleRepository;
import com.tuannq.store.repository.UsersRepository;
import com.tuannq.store.service.UserService;
import com.tuannq.store.util.ConverterUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.HashSet;
import java.util.Optional;

@Component
public class UserServiceImpl implements UserService {
    private final AuthenticationManager authenticationManager;
    private final UsersRepository usersRepository;
    private final ConverterUtils converterUtils;
    private final PasswordEncoder passwordEncoder;
    private final MessageSource messageSource;
    private final JavaMailSender javaMailSender;
    private final OTPRepository otpRepository;
    private final RoleRepository roleRepository;
    @Value("${spring.mail.username}")
    private String senderEmail;

    @Autowired
    public UserServiceImpl(
            UsersRepository usersRepository,
            ConverterUtils converterUtils,
            MessageSource messageSource,
            AuthenticationManager authenticationManager,
            JavaMailSender javaMailSender,
            OTPRepository otpRepository,
            PasswordEncoder passwordEncoder,
            RoleRepository roleRepository
    ) {
        this.otpRepository = otpRepository;
        this.javaMailSender = javaMailSender;
        this.usersRepository = usersRepository;
        this.converterUtils = converterUtils;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.messageSource = messageSource;
        this.roleRepository = roleRepository;
    }

    @Override
    public Page<Users> search(String keyword, Pageable pageable) {
        return usersRepository.search(keyword, pageable);
    }

    @Override
    public void deleteById(long id) {
        var userOpt = usersRepository.findById(id);
        if (userOpt.isEmpty())
            throw new NotFoundException(messageSource.getMessage("not-found.user.id", null, LocaleContextHolder.getLocale()).concat(String.valueOf(id)));

        usersRepository.deleteById(id);
    }

    @Override
    public Users findByEmail(String email) {
        return usersRepository.findByEmail(email);
    }

    @Transactional
    @Override
    public String sendOTPToEmail(String email) {
        Users user = usersRepository.findByEmail(email);
        if (user == null)
            throw new ArgumentException("email", messageSource.getMessage("email.not-found", null, LocaleContextHolder.getLocale()));
        var otp = generateOTP(TransactionType.FORGOT_PASSWORD, email);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("Quên mật khẩu");
        mailMessage.setFrom(senderEmail);
        mailMessage.setText("Chúng tôi đã nhận được yêu cầu đặt lại mật khẩu của bạn.\n"
                .concat("Nhập mã đặt lại mật khẩu sau đây:\t")
                .concat(otp)
                .concat("\nMã có hiệu lực trong 5 phút."));
        javaMailSender.send(mailMessage);
        return email;
    }

    @Transactional
    @Override
    public Users changePasswordWithOTP(ChangePasswordWithOTPRequest form) {
        Users user = usersRepository.findByEmail(form.getEmail());
        if (user == null)
            throw new ArgumentException("email", messageSource.getMessage("email.not-found", null, LocaleContextHolder.getLocale()));
        var otpOptional = otpRepository.findTransactionAndEmail(TransactionType.FORGOT_PASSWORD.getName(), form.getEmail());
        checkOTP(otpOptional, form.getOtp());

        user.setPassword(passwordEncoder.encode(form.getNewPassword()));
        return usersRepository.save(user);
    }

    @Transactional
    @Override
    public Users addUserByAdmin(UserFormAdmin form) throws ArgumentException {
        if (usersRepository.findByPhone(form.getPhone()) != null)
            throw new ArgumentException("phone", messageSource.getMessage("phone.exist", null, LocaleContextHolder.getLocale()));
        if (usersRepository.findByEmail(form.getEmail()) != null)
            throw new ArgumentException("email", messageSource.getMessage("email.exist", null, LocaleContextHolder.getLocale()));

        String pwd = ConverterUtils.generateRandomPassword();
        Users user = new Users(form, passwordEncoder.encode(pwd));


        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Tao moi tai khoan");
        mailMessage.setFrom(senderEmail);
        mailMessage.setText("username: ".concat(user.getEmail())
                .concat("\nPassword: ").concat(pwd));
        javaMailSender.send(mailMessage);
        return usersRepository.save(user);
    }

    @Transactional
    @Override
    public Users editUserByAdmin(Users user, UserFormAdmin form) throws ArgumentException {
        Users u1 = usersRepository.findByPhone(form.getPhone());
        if (u1 != null && !u1.getId().equals(user.getId()))
            throw new ArgumentException("phone", messageSource.getMessage("phone.exist", null, LocaleContextHolder.getLocale()));
        Users u2 = usersRepository.findByEmail(form.getEmail());
        if (u2 != null && !u2.getId().equals(user.getId()))
            throw new ArgumentException("email", messageSource.getMessage("email.exist", null, LocaleContextHolder.getLocale()));

        user.setUser(form);
        return usersRepository.save(user);
    }

    @Transactional
    @Override
    public Users updateProfile(Users user, UpdateProfileForm form) throws ArgumentException {
        Users u1 = usersRepository.findByPhone(form.getPhone());
        if (u1 != null && !u1.getId().equals(user.getId()))
            throw new ArgumentException("phone", messageSource.getMessage("phone.exist", null, LocaleContextHolder.getLocale()));
        Users u2 = usersRepository.findByEmail(form.getEmail());
        if (u2 != null && !u2.getId().equals(user.getId()))
            throw new ArgumentException("email", messageSource.getMessage("email.exist", null, LocaleContextHolder.getLocale()));

        user.setUser(form);
        return usersRepository.save(user);
    }

    @Transactional
    @Override
    public Authentication authenticate(LoginForm form) {
        Users ue = usersRepository.findExistByEmail(form.getEmail());
        if (ue == null)
            throw new BadCredentialsException(messageSource.getMessage("info-login.incorrect", null, LocaleContextHolder.getLocale()));

        if (!passwordEncoder.matches(form.getPassword(), ue.getPassword()))
            throw new BadCredentialsException(messageSource.getMessage("info-login.incorrect", null, LocaleContextHolder.getLocale()));


        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        form.getEmail(),
                        form.getPassword()
                )
        );
    }

    @Override
    public Optional<Users> findById(long id) {
        return usersRepository.findById(id);
    }

    @Override
    public Users addUserByCustomer(UserFormCustomer form) throws ArgumentException {
        if (usersRepository.findByPhone(form.getPhone()) != null)
            throw new ArgumentException("phone", messageSource.getMessage("phone.exist", null, LocaleContextHolder.getLocale()));
        if (usersRepository.findByEmail(form.getEmail()) != null)
            throw new ArgumentException("email", messageSource.getMessage("email.exist", null, LocaleContextHolder.getLocale()));
        HashSet<Role> roles = new HashSet();
        roles.add(roleRepository.findByName("ROLE_CUSTOMER"));

        Users user = new Users(form, passwordEncoder.encode(form.getPassword()), roles);
        return usersRepository.save(user);
    }

    @Override
    public void changePassword(Users user, ChangePasswordForm form) throws ArgumentException {
        // Validate password
        if (!passwordEncoder.matches(form.getOldPassword(), user.getPassword()))
            throw new ArgumentException("oldPassword", messageSource.getMessage("password.incorrect", null, LocaleContextHolder.getLocale()));

        user.setPassword(passwordEncoder.encode(form.getNewPassword()));
        usersRepository.save(user);
    }

    private String generateOTP(TransactionType transactionType, String email) {
        final String number = "0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            int randomIndex = random.nextInt(number.length());
            sb.append(number.charAt(randomIndex));
        }
        var otp = sb.toString();
        otpRepository.save(
                new OTP(transactionType.getName(),
                        passwordEncoder.encode(otp),
                        null,
                        email,
                        Instant.ofEpochMilli(System.currentTimeMillis() + 5 * 60 * 1000)
                )
        );

        return otp;
    }

    void checkOTP(Optional<OTP> otpOptional, String str) throws ArgumentException {
        var checkOTP = otpOptional.map(otp -> passwordEncoder.matches(str, otp.getOtp())
                && !Instant.now().isAfter(otp.getExpiration())
                && otp.getTotalRequest() < 100).orElse(false);
        otpOptional.map(otp -> {
            otp.setTotalRequest(otp.getTotalRequest() + 1);
            if (checkOTP) otp.setIsDeleted(true);
            return otp;
        }).map(otpRepository::save);
        if (!checkOTP) {
            throw new ArgumentException("otp", messageSource.getMessage("otp.expired", null, LocaleContextHolder.getLocale()));
        }
    }
}
