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

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.FetchMode;
import org.hibernate.annotations.Proxy;
import org.hibernate.criterion.Restrictions;

import eu.ist_phosphorus.harmony.idb.database.TransactionManager;
import eu.ist_phosphorus.harmony.idb.exceptions.database.DatabaseException;

class VIEW_DomainReservationMappingPK implements Serializable {

	private String domainName;
	private long reservationId;
	@Override
	public int hashCode() {
        final int result = this.domainName.hashCode()
        					^ (Long.valueOf(this.reservationId)).hashCode();
        return result;
	}
	@Override
	public boolean equals(Object o) {
        if (o.getClass() == VIEW_DomainReservationMapping.class) {
            return (this.hashCode() == o.hashCode());
        }
        return false;
	}
}

/**
 * Java representation of of the database entity {@link VIEW_DomainReservationMapping}
 * . This object does not contain any logic.
 * 
 * @author Alexander Zimmermann (zimmerm2@cs.uni-bonn.de)
 * @version 0.1
 */
@Entity
@Table(name = "VIEW_DomainReservationMapping")
@Proxy(lazy = false)
@IdClass(VIEW_DomainReservationMappingPK.class)
public class VIEW_DomainReservationMapping implements java.io.Serializable {

    private static final long serialVersionUID = 2920240505647918320L;

    // Fields

    /** domain name. */
    @Id
    private String domainName;

    /** Primary key of the NSP-reservation in the database. */
    @Id
    private long reservationId;

    /**
     * default constructor. this is a view in the DB, therefore no constructor.
     * 
     */
    private VIEW_DomainReservationMapping() {
        // empty
    }

    public String getDomainName() {
        return this.domainName;
    }

    public final void setDomainName(final String domainNameParam) {
        this.domainName = domainNameParam;
    }

    public long getReservationId() {
        return this.reservationId;
    }

    public final void setReservationId(final long resIdParam) {
        this.reservationId = resIdParam;
    }

    /**
     * @return hashCode of the ReservationMapping
     */
    @Override
    public final int hashCode() {
        final int result = this.domainName.hashCode()
                ^ (Long.valueOf(this.getReservationId())).hashCode();
        return result;
    }

    /**
     * @param resMapParam
     *            ReservationMapping to be checked
     * @return true if equals
     */
    public final boolean isEqual(final VIEW_DomainReservationMapping resMapParam) {
    	if(this.hashCode() == resMapParam.hashCode()) {
    		return true;
    	}
    	return false;
    }

    /**
     * @param resMapParam
     *            ReservationMapping to be compared to
     * @return -1 0 or 1
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public final int compareTo(final VIEW_DomainReservationMapping resMapParam) {
        if (this.getReservationId() < resMapParam.getReservationId()) {
            return -1;
        } else if (this.getReservationId() == resMapParam.getReservationId()) {
            return 0;
        } else {
            return 1;
        }
    }

    /**
     * @param o
     *            another ReservationMapping
     * @return true if equal, false else
     */
    @Override
    public final boolean equals(final Object o) {
        if (o.getClass() == VIEW_DomainReservationMapping.class) {
            return this.isEqual((VIEW_DomainReservationMapping) o);
        }
        return false;
    }

    @Transient
    @SuppressWarnings("unchecked")
    public static final List<VIEW_DomainReservationMapping> getMappingsForDomain(
            final String domName) throws DatabaseException {
        final List<VIEW_DomainReservationMapping> mappings = (List<VIEW_DomainReservationMapping>) 
        		(new TransactionManager(domName) {
            @Override
            protected void dbOperation() {
                this.result = this.session.createCriteria(
                        VIEW_DomainReservationMapping.class).setFetchMode("",
                        FetchMode.SELECT).add(
                        Restrictions.like("domainName", this.arg))
                        .list();
            }
        }).getResult();
        return mappings;
    }
}
