package diplomaProject.shop2.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String src;

    public Photo () {
    }

    public Photo (String src) {
        this.src = src;
    }

    public Long getId () {
        return id;
    }

    public void setId (Long id) {
        this.id = id;
    }

    public String getSrc () {
        return src;
    }

    public void setSrc (String src) {
        this.src = src;
    }

    /*@Override
    public boolean equals (Object o) {
        if (this == o) return true;
        if (o == null) return false;

        try {
            Photo photo = (Photo) o;
            if (!id.equals (photo.id)) return false;

            return src.equals (photo.src);

        } catch (ClassCastException e){
            System.out.println(e.getMessage());
            return false;
        }
    }*/

    @Override
    public boolean equals (Object o) {
        if (this == o) return true;
        if (o == null) return false;

        try {
            Photo photo = (Photo) o;
            if (id != null ? !id.equals (photo.id) : photo.id != null) return false;
            return src != null ? src.equals (photo.src) : photo.src == null;

        } catch (ClassCastException e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public int hashCode () {
        int result = id != null ? id.hashCode () : 0;
        result = 31 * result + (src != null ? src.hashCode () : 0);
        return result;
    }
}
