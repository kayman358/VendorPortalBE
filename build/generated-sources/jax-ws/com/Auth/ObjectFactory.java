
package com.Auth;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.Auth package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.Auth
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ADAuthenticatorResponse }
     * 
     */
    public ADAuthenticatorResponse createADAuthenticatorResponse() {
        return new ADAuthenticatorResponse();
    }

    /**
     * Create an instance of {@link ADUserDetails }
     * 
     */
    public ADUserDetails createADUserDetails() {
        return new ADUserDetails();
    }

    /**
     * Create an instance of {@link ADValidateUser }
     * 
     */
    public ADValidateUser createADValidateUser() {
        return new ADValidateUser();
    }

    /**
     * Create an instance of {@link ADValidateUserResponse }
     * 
     */
    public ADValidateUserResponse createADValidateUserResponse() {
        return new ADValidateUserResponse();
    }

    /**
     * Create an instance of {@link ADUserDetailsResponse }
     * 
     */
    public ADUserDetailsResponse createADUserDetailsResponse() {
        return new ADUserDetailsResponse();
    }

    /**
     * Create an instance of {@link GetGroups }
     * 
     */
    public GetGroups createGetGroups() {
        return new GetGroups();
    }

    /**
     * Create an instance of {@link ADAuthenticator }
     * 
     */
    public ADAuthenticator createADAuthenticator() {
        return new ADAuthenticator();
    }

    /**
     * Create an instance of {@link GetGroupsResponse }
     * 
     */
    public GetGroupsResponse createGetGroupsResponse() {
        return new GetGroupsResponse();
    }

}
