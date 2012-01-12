package org.openregistry.core.service;

import org.junit.Test;
import org.openregistry.core.domain.AuxiliaryIdentifier;
import org.openregistry.core.domain.AuxiliaryProgram;
import org.openregistry.core.domain.IdentifierType;
import org.openregistry.core.domain.Type;
import org.openregistry.core.domain.jpa.JpaAuxiliaryIdentifierImpl;
import org.openregistry.core.domain.jpa.JpaAuxiliaryProgramImpl;
import org.openregistry.core.domain.jpa.JpaIdentifierTypeImpl;
import org.openregistry.core.repository.ReferenceRepository;
import org.openregistry.core.service.auxiliaryprograms.AuxiliaryProgramService;
import org.springframework.test.context.ContextConfiguration;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: nah67
 * Date: 11/1/11
 * Time: 4:44 PM
 * To change this template use File | Settings | File Templates.
 */
@ContextConfiguration(locations = {"classpath:test-auxiliary-program-service-context.xml"})
public class DefaultAuxiliaryProgramServiceTests extends AbstractIntegrationTests{

    private final String TEST_IDENTIFIER_NAME ="NETID";
    private final String TEST_IDENTIFIER_VALUE ="description";

    @Inject
    private AuxiliaryProgramService programService;

    @Inject
    ReferenceRepository referenceRepository;

//    @PersistenceContext
//    private EntityManager entityManager;


    private AuxiliaryProgram auxiliaryProgram;
    private JpaAuxiliaryIdentifierImpl auxiliaryIdentifier1;
    private JpaAuxiliaryIdentifierImpl auxiliaryIdentifier2;
    private JpaAuxiliaryIdentifierImpl auxiliaryIdentifier3;


    @Test
    public void saveAuxiliaryProgram(){

//            List<AuxiliaryProgram> lap = this.auxProgramRepository.findAllAuxiliaryPrograms();
//            for(AuxiliaryProgram aptemp : lap){
//                System.out.println("Program Name: " + aptemp.getProgramName());
//                Set<AuxiliaryIdentifier> lai = aptemp.getIdentifiers();
//                for(AuxiliaryIdentifier aitemp : lai){
//                    System.out.println("Identifier Value: " + aitemp.getValue());
//                    System.out.println("Identifier Type: " + aitemp.getType());
//
//                }
//            }

        System.out.println("------------------------------------");
        this.auxiliaryProgram = new JpaAuxiliaryProgramImpl();
        this.auxiliaryIdentifier1 = new JpaAuxiliaryIdentifierImpl();
        this.auxiliaryIdentifier2 = new JpaAuxiliaryIdentifierImpl();
        this.auxiliaryIdentifier3 = new JpaAuxiliaryIdentifierImpl();

        auxiliaryProgram.setProgramName("Test Program1");
        auxiliaryProgram.setAffiliationDate(new Date(System.currentTimeMillis()));
        auxiliaryProgram.setTerminationDate(new Date(System.currentTimeMillis()));
        auxiliaryProgram.setSponsorId(new Long("1"));
        auxiliaryProgram.setSponsorType(referenceRepository.findType(Type.DataTypes.SPONSOR,Type.SponsorTypes.PERSON));

        //auxiliaryProgram.addAuxiliaryIdentifier(auxiliaryIdentifier1);

        System.out.println("Test: saveAuxiliaryProgram");
        AuxiliaryProgram ap = this.programService.saveAuxiliaryProgram(auxiliaryProgram);

        System.out.println("Program id: " +  ap.getId());

        //set the identifier
        auxiliaryIdentifier1.setCreationDate(new Date(System.currentTimeMillis()));
        auxiliaryIdentifier1.setDeleted(false);
        auxiliaryIdentifier1.setNotificationDate(new Date(System.currentTimeMillis()));
        auxiliaryIdentifier1.setPrimary(true);
        auxiliaryIdentifier1.setValue("test");
        IdentifierType netIdIdentifierType = this.referenceRepository.findIdentifierType(TEST_IDENTIFIER_NAME);
        auxiliaryIdentifier1.setType((JpaIdentifierTypeImpl) netIdIdentifierType);
        //assign the program to the identifier
        auxiliaryIdentifier1.setProgram(ap);

        //assign identifier to program
        programService.addIdentifierToProgram(auxiliaryProgram,auxiliaryIdentifier1);

        //find the data back
        List<AuxiliaryProgram> listAuxPrograms = programService.findAllAuxiliaryPrograms();

        for(AuxiliaryProgram auxProgram : listAuxPrograms){
            System.out.println("Program ID is: " + auxProgram.getId());
            //find the identifiers associated with this program
            Set<AuxiliaryIdentifier> setAuxIdentifiers = auxProgram.getIdentifiers();
            for(AuxiliaryIdentifier auxIdentifier : setAuxIdentifiers){
                System.out.println("Auxiliary Identifier: " + auxIdentifier.getValue());
            }
        }

    }
}
