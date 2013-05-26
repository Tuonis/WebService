/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ressource;

import com.miage.m1.Candidature.model.CandidatResource;
import com.miage.m1.Candidature.model.CandidatsResource;
import com.miage.m1.Candidature.model.CandidatureResource;
import com.miage.m1.Candidature.model.EtatResource;
import com.miage.m1.Candidature.model.PromotionResource;
import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;


public class RestletApplication extends Application {
  @Override
  public synchronized Restlet createInboundRoot() {
    Router router = new Router(getContext());
    //router.attach("/produits/{id}", ProduitResource.class);
    router.attach("/candidats/email={email}&mdp={mdp}", CandidatResource.class);
    router.attach("/candidats/mail={mail}", CandidatResource.class);
    router.attach("/promotions/{idPromotion}", PromotionResource.class);
    router.attach("/candidatures/promotion={promotion}", CandidatureResource.class);
    router.attach("/candidatures/etat={etat}", CandidatureResource.class);
    router.attach("/candidatures", CandidatureResource.class);
    router.attach("/candidats", CandidatsResource.class);
    router.attach("/candidat", CandidatResource.class);
    router.attach("/promotions", PromotionResource.class);
    router.attach("/etats", EtatResource.class);
    // Gerer les exceptions a notre facon
    //router.getApplication().setStatusService(new MyStatusService());
    return router;
  }
}
