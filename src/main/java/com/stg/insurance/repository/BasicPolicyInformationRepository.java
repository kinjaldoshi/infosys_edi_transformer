/**
 * 
 */
package com.stg.insurance.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.stg.insurance.data.beans.BasicPolicyInformation;

/**
 * @author kinjaldoshi
 *
 */

@Repository
public interface BasicPolicyInformationRepository extends CrudRepository<BasicPolicyInformation, String> {

	

}
