package com.web.application.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.web.application.dto.UserDTO;
import com.web.application.entity.Promotion;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
	@Autowired
	private JavaMailSender emailSender;

	public void sendEmail(String to, String subject, Promotion promotion, UserDTO userDTO)
			throws MessagingException, IOException {
		MimeMessage message = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

		helper.setTo(to);
		helper.setSubject(subject);

		String htmlContent;
		htmlContent = """
	<!DOCTYPE html>
	<html>
		<head>
    		<style>
        		body {
            		font-family: 'Arial', sans-serif;
            		background-color: #fff3cd;
            		margin: 0;
            		padding: 20px;
        		}
        		.container {
            		max-width: 600px;
            		margin: auto;
            		background-color: #ffffff;
            		padding: 20px;
            		border-radius: 10px;
            		box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
        		}
        		.header {
            		background-color: #ffc107;
            		color: white;
            		padding: 15px;
        		    text-align: center;
         		   border-radius: 10px 10px 0 0;
        		}
        		.header h1 {
        		    margin: 0;
        		    font-size: 24px;
        		}
        		p {
         		   margin: 20px 0;
         		   color: #856404;
        		}
        		.details {
            		margin: 20px 0;
            		padding: 10px;
            		background-color: #fff3cd;
            		border-radius: 5px;
            		color: #856404;
        		}
        		.footer {
        		    text-align: center;
        		    color: #856404;
        		    margin-top: 20px;
        		    font-size: 14px;
        		    padding-top: 10px;
        		    border-top: 1px solid #ffeeba;
        		}
    		</style>
		</head>
		<body>
    		<div class="container">
        		<div class="header">
            		<h1>Kính gửi khách hàng ${userDTO.getFullName()},</h1>
        		</div>
        		<p><strong>Bạn vừa được tặng mã khuyến mãi trị giá ${promotion.getDiscountValue()}%:</strong> ${promotion.getCouponCode()}</p>
        		<p>Bạn có thể nhập mã khuyến mãi trên vào đơn hàng của mình</p>
        		<div class="footer">
            		Trân trọng, <br>
            		Web bán giày
        		</div>
    		</div>
		</body>
	</html>
""";


		helper.setText(htmlContent, true);

		// Send the email
		emailSender.send(message);
	}
}
