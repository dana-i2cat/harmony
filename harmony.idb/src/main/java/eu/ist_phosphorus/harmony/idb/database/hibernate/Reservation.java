/**
*  This code is part of the Harmony System implemented in Work Package 1 
*  of the Phosphorus project. This work is supported by the European 
*  Comission under the Sixth Framework Programme with contract number 
*  IST-034115.
*
*  Copyright (C) 2006-2009 Phosphorus WP1 partners. Phosphorus Consortium.
*  http://ist-phosphorus.eu/
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */


package eu.ist_phosphorus.harmony.idb.database.hibernate;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.log4j.Logger;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.annotations.Proxy;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;

import eu.ist_phosphorus.harmony.common.serviceinterface.databinding.jaxb.CreateReservationType;
import eu.ist_phosphorus.harmony.common.serviceinterface.databinding.jaxb.GetStatusResponseType;
import eu.ist_phosphorus.harmony.common.serviceinterface.databinding.jaxb.GetStatusType;
import eu.ist_phosphorus.harmony.common.serviceinterface.databinding.jaxb.ServiceConstraintType;
import eu.ist_phosphorus.harmony.common.serviceinterface.databinding.jaxb.exceptions.EndpointNotFoundFaultException;
import eu.ist_phosphorus.harmony.common.serviceinterface.databinding.jaxb.exceptions.InvalidReservationIDFaultException;
import eu.ist_phosphorus.harmony.common.serviceinterface.databinding.utils.WebserviceUtils;
import eu.ist_phosphorus.harmony.common.utils.Helpers;
import eu.ist_phosphorus.harmony.common.utils.PhLogger;
import eu.ist_phosphorus.harmony.common.utils.Tuple;
import eu.ist_phosphorus.harmony.idb.database.TransactionManager;
import eu.ist_phosphorus.harmony.idb.database.TransactionManagerDelete;
import eu.ist_phosphorus.harmony.idb.database.TransactionManagerLoad;
import eu.ist_phosphorus.harmony.idb.exceptions.database.DatabaseException;

/**
 * Java representation of of the database entity {@link Reservation}.
 */
@Entity
@Table(name = "Reservation")
@Proxy(lazy = false)
public class Reservation implements java.io.Serializable {

    private static Logger logger = PhLogger.getLogger();
    // Fields

    /**
     *
     */
    private static final long serialVersionUID = -6417309812831412979L;

    /**
     * reservation id.
     */
    private long reservationId;

    /**
     * URL of consumer wishing to be notified about changes related to this
     * reservation.
     */
    private String consumerUrl;

    /**
     * timeout of the reservation.
     */
    private Date timeout;

    /**
     * job id of the reservation.
     */
    private long jobId;

    /**
     * job id of the reservation.
     */
    private String gri;

    /**
     * job id of the reservation.
     */
    private String token;

    /**
     * services of the reservation.
     */
    private Map<Integer, Service> services = new HashMap<Integer, Service>(0);

    // Constructors

    /** Default constructor. */
    public Reservation() {
        // empty
    }

    /**
     * constructor with id and timeout.
     * 
     * @param reservationIdParam
     *            Reservation ID.
     * @param timeoutParam
     *            timeout Date.
     */
    public Reservation(final long reservationIdParam, final Date timeoutParam) {
        super();
        this.reservationId = reservationIdParam;
        this.timeout = timeoutParam;
    }

    /**
     * Constructor with initial values for all fields of primitive type.
     * 
     * @param reservationIdParam
     *            id of the reservation
     * @param consumerUrlParam
     *            consumer url
     * @param timeoutParam
     *            timeout of the reservation
     * @param jobIdParam
     */
    public Reservation(final long reservationIdParam,
            final String consumerUrlParam, final Date timeoutParam,
            final long jobIdParam) {
        this.reservationId = reservationIdParam;
        this.consumerUrl = consumerUrlParam;
        this.timeout = timeoutParam;
        this.jobId = jobIdParam;
    }

    /**
     * Constructor with initial values for all fields of primitive type.
     * 
     * @param reservationIdParam
     *            id of the reservation
     * @param consumerUrlParam
     *            consumer url
     * @param timeoutParam
     *            timeout of the reservation
     * @param jobIdParam
     */
    public Reservation(final long reservationIdParam,
            final String consumerUrlParam, final long jobIdParam) {
        this.reservationId = reservationIdParam;
        this.consumerUrl = consumerUrlParam;
        this.timeout = new Date();
        this.jobId = jobIdParam;
    }

    // Property accessors

    /**
     * Retrieve the Reservation ID, which is also the primary database key.
     * 
     * @return Reservation ID.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public final long getReservationId() {
        return this.reservationId;
    }

    /**
     * Set the Reservation ID, which is also the primary database key.
     * 
     * @param reservationIdParam
     *            Reservation ID.
     */
    public final void setReservationId(final long reservationIdParam) {
        this.reservationId = reservationIdParam;
    }

    /**
     * @return consumer url
     */
    @Basic(optional = true)
    public final String getConsumerUrl() {
        return this.consumerUrl;
    }

    /**
     * @param consumerUrlParam
     *            consumer url
     */
    public final void setConsumerUrl(final String consumerUrlParam) {
        this.consumerUrl = consumerUrlParam;
    }

    /**
     * @return timeout of the reservation
     */
    @Basic(optional = true)
    public final Date getTimeout() {
        return this.timeout;
    }

    /**
     * @param timeoutParam
     *            timeout of the reservation
     */
    public final void setTimeout(final Date timeoutParam) {
        this.timeout = timeoutParam;
    }

    /**
     * @return job id
     */
    public final long getJobId() {
        return this.jobId;
    }

    /**
     * @param jobIdParam
     *            job id
     */
    public final void setJobId(final long jobIdParam) {
        this.jobId = jobIdParam;
    }

    /**
     * @return the gri
     */
    @Basic(optional = true)
    public final String getGri() {
        return this.gri;
    }

    /**
     * @param gri
     *            the gri to set
     */
    public final void setGri(final String gri) {
        this.gri = gri;
    }

    /**
     * @return the token
     */
    @Basic(optional = true)
    public final String getToken() {
        return this.token;
    }

    /**
     * @param token
     *            the token to set
     */
    public final void setToken(final String token) {
        this.token = token;
    }

    /**
     * @return set of services
     */
    @OneToMany(mappedBy = "reservation", fetch = FetchType.LAZY, cascade = { javax.persistence.CascadeType.ALL })
    @MapKey(name = "serviceId")
    public final Map<Integer, Service> getServices() {
        return this.services;
    }

    /**
     * @param servicesParam
     *            set of services
     */
    public final void setServices(final Map<Integer, Service> servicesParam) {
        this.services = servicesParam;
    }

    /**
     * Add a service to this reservation.
     * 
     * @param service
     *            Service to be added.
     */
    public final void addService(final Service servicesParam) {
        servicesParam.setReservation(this);
        getServices().put(new Integer(servicesParam.getServiceId()),
                servicesParam);
    }

    /**
     * Retrieve service with a specific ID. If the reservation was previously
     * loaded from the database, an additional loadServices() call must be made
     * to actually load the services associated with this reservation.
     * 
     * @param serviceId
     *            Service ID of requested service.
     * @return Service with the given Service ID, or null if not found.
     */
    @Transient
    public final Service getService(final int serviceId) {
        return getServices().get(new Integer(serviceId));
    }

    /**
     * @param resParam
     *            reservation to be checked
     * @return true if equals
     */
    public final boolean isEqual(final Reservation resParam) {
        if (this.hashCode() == resParam.hashCode()) {
            return true;
        }
        return false;
    }

    /**
     * @param o
     * @return
     */
    @Override
    public final boolean equals(final Object o) {
        if (o.getClass() == Reservation.class) {
            return isEqual((Reservation) o);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result =
                new Long(this.getReservationId()).hashCode()
                        ^ new Long(this.getJobId()).hashCode()
                        ^ ((this.getConsumerUrl() == null) ? 0 : this
                                .getConsumerUrl().hashCode())
                        ^ new Long(Helpers.trimDateToSeconds(this.getTimeout()))
                                .hashCode();

        // in the underlying objects, don't use the hashCode()-method, because
        // this can end in
        // dependency-circles. Instead only use the DB-primary key for the hash.
        // for (Service s : getServices().values()) {
        // result ^= new Long(s.getPK_service()).hashCode();
        // }

        return result;
    }

    /**
     * @return copy of Reservation
     */
    @Transient
    public final Reservation getCopy() {
        Reservation copy =
                new Reservation(this.reservationId, this.consumerUrl,
                        this.timeout, this.jobId);
        for (Service s : getServices().values()) {
            copy.addService(s);
        }
        return copy;
    }

    @Override
    public final String toString() {
        return "<reservationId>" + getReservationId() + "</reservationId>"
                + "<consumerUrl>" + getConsumerUrl()
                + "</consumerUrl><timeout>" + getTimeout()
                + "</timeout><jobId>" + getJobId() + "</jobId>";
    }

    /**
     * @param resParam
     *            reservation to be compared to
     * @return -1 0 or 1
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public final int compareTo(final Reservation resParam) {
        if (this.reservationId < resParam.getReservationId()) {
            return -1;
        } else if (this.reservationId == resParam.getReservationId()) {
            return 0;
        } else {
            return 1;
        }
    }

    /**
     * Create Reservation object from JAXB input.
     * 
     * @param jaxb
     *            JAXB input
     * @return Reservation object created from JAXB input.
     * @throws DatabaseException
     * @throws EndpointNotFoundFaultException
     */
    public static Reservation fromJaxb(final CreateReservationType jaxb)
            throws EndpointNotFoundFaultException, DatabaseException {
        Reservation result = new Reservation();
        result.setReservationId(0l);
        result.setConsumerUrl(jaxb.getNotificationConsumerURL());
        if (jaxb.isSetJobID()) {
            result.setJobId(jaxb.getJobID());
        } else {
            result.setJobId(0);
        }
        result.setTimeout(new Date());
        result.setServices(new HashMap<Integer, Service>(0));
        for (ServiceConstraintType jaxbService : jaxb.getService()) {
            Service s = Service.fromJaxb(jaxbService);
            s.setReservation(result);
            result.addService(s);
        }

        if (null != jaxb.getGRI()) {
            result.setGri(jaxb.getGRI());
        }

        if (null != jaxb.getToken()) {
            result.setToken(jaxb.getToken());
        }

        return result;
    }

    /**
     * Create Reservation object from JAXB input.
     * 
     * @param jaxb
     *            JAXB input
     * @return Reservation object created from JAXB input.
     * @throws DatabaseException
     * @throws EndpointNotFoundFaultException
     */
    public static Reservation fromJaxb(
            final eu.ist_phosphorus.harmony.common.serviceinterface.databinding.jaxb.IsAvailableType jaxb)
            throws EndpointNotFoundFaultException, DatabaseException {
        Reservation result = new Reservation();
        result.setReservationId(0L);
        result.setJobId(0);
        result.setTimeout(new Date());
        result.setServices(new HashMap<Integer, Service>(0));
        for (ServiceConstraintType jaxbService : jaxb.getService()) {
            result.addService(Service.fromJaxb(jaxbService));
        }
        return result;
    }

    /**
     * Load reservation from the DB. This function does not load the services
     * that belong to the reservation - there is an additional method for doing
     * this!
     * 
     * @param domainName
     *            ID of domain to be retrieved from the database.
     * @return {@link Domain} for given ID, or null if domain was not found.
     * @throws DatabaseException
     */
    public static final Reservation load(long resvID) throws DatabaseException {
        return (Reservation) (new TransactionManagerLoad(Reservation.class,
                Long.valueOf(resvID))).getResult();
    }

    /**
     * Load reservation from the DB. This function does not load the services
     * that belong to the reservation - there is an additional method for doing
     * this!
     * 
     * @param domainName
     *            ID of domain to be retrieved from the database.
     * @return {@link Domain} for given ID, or null if domain was not found.
     * @throws DatabaseException
     * @throws InvalidReservationIDFaultException
     */
    public static final Reservation load(String resvID)
            throws DatabaseException, InvalidReservationIDFaultException {
        return load(WebserviceUtils.convertReservationID(resvID));
    }

    /**
     * load all existing reservations from DB
     * 
     * @return
     * @throws DatabaseException
     */
    @SuppressWarnings("unchecked")
    public static final Set<Reservation> loadAll() throws DatabaseException {
        return (Set<Reservation>) (new TransactionManager() {
            @Override
            protected void dbOperation() {
                Set<Reservation> result = new HashSet<Reservation>();
                final List<Reservation> tmpReservation =
                        this.session.createCriteria(Reservation.class)
                                .setFetchMode("Reservation", FetchMode.SELECT)
                                .list();
                for (Reservation r : tmpReservation) {
                    result.add(r);
                }
                this.result = result;
            }
        }).getResult();
    }

    /**
     * load all reservations from DB which lie in a given time-period.
     */
    @SuppressWarnings("unchecked")
    public static final Set<Reservation> loadAllInPeriod(Date startTime,
            Date endTime) throws DatabaseException {
        return (Set<Reservation>) (new TransactionManager(
                new Tuple<Date, Date>(startTime, endTime)) {
            @Override
            protected void dbOperation() {
                Set<Reservation> result = new HashSet<Reservation>();
                Tuple<Date, Date> times = (Tuple<Date, Date>) this.arg;
                // select all reservation-IDs from ReservationPeriod-view, which
                // lie in the given time-period
                DetachedCriteria ids =
                        DetachedCriteria
                                .forClass(VIEW_ReservationPeriod.class)
                                .setProjection(
                                        Property.forName("reservationId"))
                                .add(
                                        Restrictions
                                                .disjunction()
                                                .add(
                                                        Restrictions
                                                                .between(
                                                                        "startTime",
                                                                        times
                                                                                .getFirstElement(),
                                                                        times
                                                                                .getSecondElement()))
                                                .add(
                                                        Restrictions
                                                                .between(
                                                                        "endTime",
                                                                        times
                                                                                .getFirstElement(),
                                                                        times
                                                                                .getSecondElement()))
                                                .add(
                                                        Restrictions
                                                                .and(
                                                                        Restrictions
                                                                                .le(
                                                                                        "startTime",
                                                                                        times
                                                                                                .getFirstElement()),
                                                                        Restrictions
                                                                                .ge(
                                                                                        "endTime",
                                                                                        times
                                                                                                .getSecondElement()))));

                // select all reservations from DB accoring to the IDs from step
                // one
                final List<Reservation> tmpReservation =
                        this.session.createCriteria(Reservation.class)
                                .add(
                                        Subqueries.propertyIn("reservationId",
                                                ids))
                                // use join-select, because all infos will be
                                // needed later
                                .setFetchMode("Reservation", FetchMode.JOIN)
                                .list();
                for (Reservation r : tmpReservation) {
                    result.add(r);
                }
                this.result = result;
            }
        }).getResult();
    }

    /**
     * Get reservations summarized under one job-Id.
     * 
     * @param jobId
     *            Id which summarizes reservations
     * @return List of Reservations summarized under the committed job-Id
     * @throws DatabaseException
     */
    @SuppressWarnings("unchecked")
    public static final List<Reservation> loadJob(final long jobId)
            throws DatabaseException {
        return (List<Reservation>) (new TransactionManager("jobID=" + jobId) {
            @Override
            protected void dbOperation() {
                this.result =
                        this.session.createCriteria(Reservation.class)
                                .setFetchMode("", FetchMode.SELECT).add(
                                        Expression.sql((String) this.arg))
                                .list();
            }
        }).getResult();
    }

    public final void save(Session session) {
        session.saveOrUpdate(this);
    }

    public final void save() throws DatabaseException {
        new TransactionManager() {
            @Override
            protected void dbOperation() throws Exception {
                save(this.session);
            }
        };
    }

    public final void delete() throws DatabaseException {
        new TransactionManagerDelete(this);
    }

    public final void delete(Session session) throws DatabaseException {
        new TransactionManager() {
            @Override
            protected void dbOperation() throws Exception {
                delete(this.session);
            }
        };
    }

    public final void loadOrCreateUserEndpoints() throws DatabaseException,
            EndpointNotFoundFaultException {
        for (Service s : getServices().values()) {
            s.loadOrCreateUserEndpoints();
        }
    }

    @Transient
    public HashMap<Domain, Reservation> getReservationsForDomains() {
        HashMap<Domain, Reservation> domainReservations =
                new HashMap<Domain, Reservation>();
        for (Service service : this.getServices().values()) {
            for (Connections connections : service.getConnections().values()) {
                for (NrpsConnections nrpsConnection : connections
                        .getNrpsConnections().values()) {
                    Reservation reservationForDomain =
                            domainReservations.get(nrpsConnection.getDomain());
                    if (null == reservationForDomain) {
                        reservationForDomain = new Reservation();
                        // transfer GRI and token
                        reservationForDomain.setGri(this.getGri());
                        reservationForDomain.setToken(this.getToken());
                        domainReservations.put(nrpsConnection.getDomain(),
                                reservationForDomain);
                    }
                    Service serviceForDomain =
                            reservationForDomain.getService(service
                                    .getServiceId());
                    if (null == serviceForDomain) {
                        serviceForDomain = service.getCopy();
                        serviceForDomain.getConnections().clear();
                        reservationForDomain.addService(serviceForDomain);
                    }
                    serviceForDomain.addConnection(nrpsConnection
                            .toConnnections(connections));
                }
            }
        }

        return domainReservations;
    }

    public final CreateReservationType toJaxb() {
        CreateReservationType createReservationType =
                new CreateReservationType();
        for (Service service : this.getServices().values()) {
            createReservationType.getService().add(service.toJaxb());
        }
        
        if(this.gri != null) {
            createReservationType.setGRI(this.gri);
        }
        
        if(this.token != null) {
            createReservationType.setToken(this.token);
        }
        
        return createReservationType;
    }

    @Transient
    public final String getDebugInfo() {
        String serviceOut = "";
        for (Service service : getServices().values()) {
            serviceOut += service.getDebugInfo();
        }
        return "JobId: " + this.jobId + "\n ReservarionId: "
                + this.reservationId + "\n ConsumerUrl: " + this.consumerUrl
                + "\n Timeout: " + this.timeout + "\n Services" + serviceOut;
    }

    @Transient
    public final Hashtable<Domain, GetStatusType> getGetStatusType() {
        HashMap<String, GetStatusType> getStatusTypeMap =
                new HashMap<String, GetStatusType>(0);
        HashMap<String, MAPNRPSResvID> mappingNrps =
                new HashMap<String, MAPNRPSResvID>(0);
        Hashtable<Domain, GetStatusType> returnType =
                new Hashtable<Domain, GetStatusType>();
        HashMap<String, Domain> domainMap = new HashMap<String, Domain>();
        logger.trace("Current status of Reservation: \n" + getDebugInfo());
        try {
            for (MAPNRPSResvID resvID : MAPNRPSResvID
                    .getMappingForReservation(this)) {
                mappingNrps.put(resvID.getDomain().getName(), resvID);
            }
        } catch (DatabaseException e) {
            logger.error("could not load mapping for reservation: "
                    + getReservationId(), e);
            return returnType;
        }
        for (Service service : getServices().values()) {
            // build get Status type
            for (Connections connection : service.getConnections().values()) {
                for (NrpsConnections nc : connection.getNrpsConnections()
                        .values()) {
                    domainMap.put(nc.getDomain().getName(), nc.getDomain());
                    if (!WebserviceUtils.isFinalStatus(nc.getStatusType()))
                        if (!getStatusTypeMap.containsKey(nc.getDomain()
                                .getName())) {
                            GetStatusType gs = new GetStatusType();
                            gs.setReservationID(WebserviceUtils
                                    .convertReservationID(mappingNrps.get(
                                            nc.getDomain().getName())
                                            .getnrpsReservationId()));
                            gs.getServiceID().add(
                                    new Integer(service.getServiceId()));
                            getStatusTypeMap.put(nc.getDomain().getName(), gs);
                        } else {
                            GetStatusType gs =
                                    getStatusTypeMap.get(nc.getDomain()
                                            .getName());
                            if (!Helpers.isInList(gs.getServiceID(), service
                                    .getServiceId())) {
                                gs.getServiceID().add(
                                        new Integer(service.getServiceId()));
                            }
                        }
                }
            }
        }
        for (Entry<String, GetStatusType> entry : getStatusTypeMap.entrySet()) {
            returnType.put(domainMap.get(entry.getKey()), entry.getValue());
        }
        return returnType;
    }

    @Transient
    public GetStatusResponseType getGetStatusResponseType(
            List<Integer> serviceIds) {
        if (!(null == serviceIds)) {
            GetStatusResponseType gsrt = new GetStatusResponseType();
            for (Integer integer : serviceIds) {
                gsrt.getServiceStatus().add(
                        getService(integer.intValue()).getServiceStatus());
            }
            return gsrt;
        }
        return getGetStatusResponseType();

    }

    @Transient
    public GetStatusResponseType getGetStatusResponseType() {
        GetStatusResponseType gsrt = new GetStatusResponseType();
        for (Service service : getServices().values()) {
            gsrt.getServiceStatus().add(service.getServiceStatus());
        }
        return gsrt;
    }
}
