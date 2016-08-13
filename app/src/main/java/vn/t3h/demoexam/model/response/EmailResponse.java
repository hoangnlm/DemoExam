package vn.t3h.demoexam.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import vn.t3h.demoexam.model.Email;

/**
 * Created by Hoang on 8/12/16.
 */

public class EmailResponse extends BaseResponse {
    @SerializedName("emails")
    @Expose
    private List<Email> emails = new ArrayList<Email>();

    /**
     *
     * @return
     * The emails
     */
    public List<Email> getEmails() {
        return emails;
    }

    /**
     *
     * @param emails
     * The emails
     */
    public void setEmails(List<Email> emails) {
        this.emails = emails;
    }
}
