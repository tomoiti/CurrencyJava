package br.com.zbra.hello.currency;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.VoidWork;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/currencies")
public class CurrencyController {

    static {
        ObjectifyService.register(Currency.class);
    }

    @RequestMapping(value = "", method = RequestMethod.GET, produces = "application/json")
    public Currency[] list() {
        return ObjectifyService.ofy().load().type(Currency.class).list().toArray(new Currency[0]);
    }

    @RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json")
    public Currency create(@RequestBody Currency currency) {
        ObjectifyService.ofy().save().entity(currency).now();
        return currency;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public Currency get(@PathVariable Long id) {
        return ObjectifyService.ofy().load().key(Key.create(Currency.class, id)).now();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id) {
        ObjectifyService.ofy().delete().key(Key.create(Currency.class, id)).now();
    }

    @RequestMapping(value = "/sync", method = RequestMethod.GET, produces = "application/json")
    public Currency[] sync() {
        ObjectifyService.ofy().transact(new VoidWork() {
            @Override
            public void vrun() {

            }
        });
        return list();
    }
}
