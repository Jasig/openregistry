package org.openregistry.core.web.resources;

import javax.ws.rs.core.MultivaluedMap;
import java.io.Serializable;
import java.util.Map;

/**
 * A simple Java bean encapsulating an incoming request for addition to the registry of
 * a person record from typical upstream systems of record.
 * This bean should typically be used to unmarshal serialized representation of an incoming person record
 * such as HTTP's form urlencoded, JSON, XML, etc. The unmarshaling of the various representations as well
 * as the validation of the payload and the minimum set of the required fields is done
 * in the corresponding constructors.
 * <p/>
 * All the fields are of <i>simple</i> types such as String, Number, etc. The Date is represented as Strings
 * and therefore clients of this class should take care of the necessary conversions/formatting/semantical validation.
 * <p/>
 * This class is immutable.
 *
 * @author Dmitriy Kopylenko
 * @since 1.0
 */
public class PersonRequestRepresentation implements Serializable {

    private static final long serialVersionUID = -1085902781173568577L;

    /* Required fields *************/
    private Long systemOfRecordId;

    private Long systemOfRecordPersonId;

    private String firstName;

    private String lastName;

    private String email;

    private String mobilePhoneNumber;

    private String mobileCarrier;

    /* Optional fields *************/
    private String dateOfBirth;

    private String ssn;

    private String ruId;

    private String netId;

    private String gender;

    private String addressLine1;

    private String addressLine2;

    private String city;

    private String region;

    private String postalCode;

    /**
     * Un-marshal the incoming form payload
     *
     * @param personFormPayload representation of an incoming person
     * @throws IllegalArgumentException if any of the required fields are missing
     *                                  or sor IDS are in the numerical form. There are no other validations performed.
     */
    public PersonRequestRepresentation(MultivaluedMap<String, String> personFormPayload) throws IllegalArgumentException {
        if (!checkRequiredData(personFormPayload)) {
            throw new IllegalArgumentException("The request either does not contain all the required data " +
                    "or some form fields are misspelled. The submitted data is " + personFormPayload);
        }
        try {
            this.systemOfRecordId = Long.valueOf(personFormPayload.getFirst("sorSourceId"));
            this.systemOfRecordPersonId = Long.valueOf(personFormPayload.getFirst("sorPersonId"));
        }
        catch (NumberFormatException ex) {
            throw new IllegalArgumentException("'sorSourceId' and 'sorPersonId' fields must be numbers. The submitted " +
                    "values are: [sorSourceId: " + personFormPayload.getFirst("sorSourceId") + "]; " +
                    "[sorPersonId: " + personFormPayload.getFirst("sorPersonId") + "]");
        }
        this.firstName = personFormPayload.getFirst("firstName");
        this.lastName = personFormPayload.getFirst("lastName");
        this.email = personFormPayload.getFirst("email");
        this.mobilePhoneNumber = personFormPayload.getFirst("mobilePhoneNumber");
        this.mobileCarrier = personFormPayload.getFirst("mobileCarrier");
        this.dateOfBirth = personFormPayload.getFirst("dateOfBirth");
        this.ssn = personFormPayload.getFirst("ssn");
        this.ruId = personFormPayload.getFirst("ruId");
        this.netId = personFormPayload.getFirst("netId");
        this.gender = personFormPayload.getFirst("gender");
        this.addressLine1 = personFormPayload.getFirst("addressLine1");
        this.addressLine2 = personFormPayload.getFirst("addressLine2");
        this.city = personFormPayload.getFirst("city");
        this.region = personFormPayload.getFirst("region");
        this.postalCode = personFormPayload.getFirst("postalCode");
    }

    private boolean checkRequiredData(MultivaluedMap<String, String> formPayload) {
        return (formPayload.getFirst("sorSourceId") != null
                && formPayload.getFirst("sorPersonId") != null
                && formPayload.getFirst("firstName") != null
                && formPayload.getFirst("lastName") != null
                && formPayload.getFirst("email") != null
                && formPayload.getFirst("mobilePhoneNumber") != null
                && formPayload.getFirst("mobileCarrier") != null);
    }
}
