package com.workintech.s17d2.dto;
import com.workintech.s17d2.model.Developer;

//dto --> data transfer object
//        hem objeyi genişletmek için ben de objede fazladan göndermesi tehlikeli olabilecek objeleri gönderebilmek için kullanılır.

//Mevcut developer sınıfını wrap ediyoruz.
//mevcut developerı da return edebilirdik save methodunda ama endpointte create işlemi yapıldıktan sonra bir status bir de mesaj değerleri dönsün istiyoruz.
public class DeveloperResponse {
    private Developer developer;
    private int status;
    private String message;

    public DeveloperResponse(Developer developer, int status, String message) {
        this.developer = developer;
        this.status = status;
        this.message = message;
    }

    public Developer getDeveloper() {
        return developer;
    }
    public void setDeveloper(Developer developer) {
        this.developer = developer;
    }
    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
