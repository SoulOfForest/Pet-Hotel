/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dal.LogDAO;
import dal.PageVisitDAO;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author ViruSs0209
 */
@Path("pageVisit")
public class PageVisitResource {
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    
    public Response getAll() {
        PageVisitDAO db = new PageVisitDAO();
        
        return Response.status(200).entity(db.getAll()).build();
    }
}
