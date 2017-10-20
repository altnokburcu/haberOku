package Model;

public class NewsMO {
    String Id;                    //Haberin id'si
    String CreatedDate;           //Haberin oluşturulma tarihi
    String Description;           //Haberin içeriği
    String ImgUrl;                //Haberin resmi
    String Title;                 //Haberin başlığı
    String Url;                   //Haberin url'i

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getImgUrl() {
        return ImgUrl;
    }

    public void setImgUrl(String imgUrl) {
        ImgUrl = imgUrl;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    public String getId() {

        return Id;
    }

    public void setId(String id) {
        Id = id;
    }
}