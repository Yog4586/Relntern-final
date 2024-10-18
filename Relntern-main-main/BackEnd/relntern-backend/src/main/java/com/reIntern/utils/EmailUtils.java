package com.reIntern.utils;

import java.sql.Date;

import com.reIntern.model.Mentor;

public class EmailUtils {
	
//    public static String getOtpEmailMessage(String username, String otpCode) {
//        return "Dear " + username + ",\n\n"
//                + "Your One-Time Password (OTP) for login is: " + otpCode + "\n\n"
//                + "Please use this OTP to complete your login process.\n\n"
//                + "If you did not request this OTP, please ignore this email.\n\n"
//                + "Thank you,\n"
//                + "Reliance Industries Ltd";
//    }

	public static String getEmailMessage(String fullname, String association, String projectname, String mentor, Mentor mentorDetails,
			Date startdate, Date enddate, String location) {
		return "<html>" +

                "<body>" +

                "<p>Dear " + fullname + ",</p>" +

                "<p>We are pleased to offer you an internship with Reliance Industries Limited. You will be working with our Petchem - IT Team.</p>" +

                "<p>Pl. find your project details as below:</p>" +

                "<p><strong>Project Title:</strong> " + projectname + "<br>" +

                "<strong>Project Guide:</strong> " + mentor + "<br>" +

                "<strong>Duration of Internship:</strong> " + startdate + " to " + enddate + "<br>" +

                "<strong>Location:</strong> " + location + "</p>" +

                "<p>Since this internship will be purely for learning purpose, there will be no stipend paid to you. There will be no accommodation provided to you.</p>" +

                "<p>Kindly note that you will have to make your own arrangement for stay and travel to & from the location assigned during the tenure of your internship.</p>" +

                "<p>Please confirm your acceptance of this internship offer via email reply so we may guide you on the pre-joining formalities.</p>" +

                "<p>Looking forward to offering a rich learning experience to you!</p>" +

                "<br>" +

                "<p>Regards,</p>" +

                "<p>Aashdeep Kaur<br>" +

                "Petchem HR<br>" +

                "Reliance Industries Limited<br>" +

                "Reliance Corporate Park | Building No. 8 ‘B’ Wing, First Floor WS 277 | Thane-Belapur Road|Ghansoli | Navi Mumbai 400 701<br>" +

                "Contact: +912279678619</p>" +

                "<br>" +

                "<p><strong>Confidentiality Warning:</strong> This message and any attachments are intended only for the use of the intended recipient(s), are confidential and may be privileged. If you are not the intended recipient, you are hereby notified that any review, re-transmission, conversion to hard copy, copying, circulation or other use of this message and any attachments is strictly prohibited. If you are not the intended recipient, please notify the sender immediately by return email and delete this message and any attachments from your system.</p>" +

                "<p><strong>Virus Warning:</strong> Although the company has taken reasonable precautions to ensure no viruses are present in this email, the company cannot accept responsibility for any loss or damage arising from the use of this email or attachment.</p>" +

                "</body>" +

                "</html>";
	}
}