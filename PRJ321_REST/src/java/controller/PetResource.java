/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dal.PetDAO;
import java.util.ArrayList;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import model.Pet;

/**
 *
 * @author ViruSs0209
 */
@Path("pets")
public class PetResource {
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})

    public Response getAll() {
        PetDAO db = new PetDAO();

        ArrayList<Pet> pets = db.getAll();

        return Response.status(200).entity(pets).build();

    }

    @GET
    @Path("search/{userEmail}")
    @Produces({MediaType.APPLICATION_JSON})

    public Response searchPetOwnerByEmail(@PathParam("userEmail") String userEmail, String x) {
        PetDAO db = new PetDAO();

        ArrayList<Pet> searchByOwner = db.searchByOwner(userEmail);
        System.out.println(userEmail);
        return Response.status(200).entity(searchByOwner).build();

    }

    @DELETE
    @Path("delete")
    @Produces({ MediaType.TEXT_PLAIN})

    public String deletePets(String x) {
        PetDAO db = new PetDAO();

        boolean hi = db.checkBookedPet(x);
       
        
        if (db.checkBookedPet(x)) {
            return "booked";
        } else {
             boolean isSucceed = db.delete(x);

            if (isSucceed) {
                return "success";
            } else {
                return "fail";
            }
        }
        
    }
}
