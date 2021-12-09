package hotel.service;

import java.util.Optional;

import hotel.model.Guest;
import hotel.repository.GuestRepository;
import hotel.repository.postgres.GuestRepositoryPostgres;

public class GuestService {
    public GuestRepository gr;

    public GuestService() {
        gr = new GuestRepositoryPostgres();
    }

    public boolean doesExist(String email) {

        Guest guest = new Guest();
        guest.email = email;

        try {
            Optional<Guest> gO = gr.getByEmail(guest);
            if (gO.isEmpty()) {
                return false;
            }
         } finally {}
        
        return true;
    }
}
