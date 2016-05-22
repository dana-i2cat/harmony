package org.opennaas.extensions.gmpls.serviceinterface.databinding.jaxb.exceptions;

import org.opennaas.extensions.gmpls.serviceinterface.databinding.jaxb.CreatePathFault;
import org.opennaas.extensions.idb.serviceinterface.databinding.jaxb.BaseFaultType;
import org.opennaas.extensions.gmpls.client.utils.GmplsFaultException;

/**
 * Autogenerated Fault Exception.
 *
 * Generated by scripts/generateFaultExceptions.java
 * Created on Thu Oct  9 15:04:28 2008
 */
public class CreatePathFaultException
        extends GmplsFaultException {

    /**
     * Autogenerated Constructor.
     *
     * @param message Message to be thrown
     * @param cause   Originator exception
     */
    public CreatePathFaultException(final String message,
            final Throwable cause) {
        super(new CreatePathFault(), message, cause);
        // TODO Auto-generated constructor stub
    }

    /**
     * Autogenerated Constructor.
     *
     * @param message Message to be thrown
     */
    public CreatePathFaultException(final String message) {
        super(new CreatePathFault(), message);
        // TODO Auto-generated constructor stub
    }

    /**
     * Autogenerated Constructor.
     *
     * @param cause   Originator exception
     */
    public CreatePathFaultException(final Throwable cause) {
        super(new CreatePathFault(), cause);
        // TODO Auto-generated constructor stub
    }

    /**
     * Autogenerated Constructor.
     */
    public CreatePathFaultException() {
        super(new CreatePathFault());
        // TODO Auto-generated constructor stub
    }

    /**
     * Autogenerated Extension Constructor.
     *
     * @param fault BaseFault
     * @param message Message to be thrown
     * @param cause   Originator exception
     */
    public CreatePathFaultException(final BaseFaultType fault, final String message,
            final Throwable cause) {
        super(fault, message, cause);
        // TODO Auto-generated constructor stub
    }

    /**
     * Autogenerated Extension Constructor.
     *
     * @param fault BaseFault
     * @param message Message to be thrown
     */
    public CreatePathFaultException(final BaseFaultType fault, final String message) {
        super(fault, message);
        // TODO Auto-generated constructor stub
    }

    /**
     * Autogenerated Extension Constructor.
     *
     * @param fault BaseFault
     * @param cause   Originator exception
     */
    public CreatePathFaultException(final BaseFaultType fault, final Throwable cause) {
        super(fault, cause);
        // TODO Auto-generated constructor stub
    }

    /**
     * Autogenerated Extension Constructor.
     *
     * @param fault BaseFault
     */
    public CreatePathFaultException(final BaseFaultType fault) {
        super(fault);
        // TODO Auto-generated constructor stub
    }

}
