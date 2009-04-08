package org.openregistry.core.web.resources;

import java.io.Serializable;

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

}
