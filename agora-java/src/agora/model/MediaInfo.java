package agora.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "mediainfo")
public class MediaInfo implements java.io.Serializable {
	public Integer id;
	public String title = "";
	private String url = "";
	private String category;
	private int subId;
	private String about;
	private String company;
	private String primary_language;
	private String scheduled_NL_frequency;
	private String scheduled_SC_frequency;
	
	private String currency;
	private String sc;
	private String newsletter;
	
	
	private String includes;
	private String comments = "";
	private String media_kit = "";
	private String Access = "";
	private String clientele = "";
	public Subscription subscription;
	public Integer parent_id;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	@OneToOne
    @JoinColumn(name="subscription_id")
	public Subscription getSubscription() {
		return subscription;
	}

	public void setSubscription(Subscription subscription) {
		this.subscription = subscription;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	
	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	

	public String getMedia_kit() {
		return media_kit;
	}

	public void setMedia_kit(String media_kit) {
		this.media_kit = media_kit;
	}

	

	public String getClientele() {
		return clientele;
	}

	public void setClientele(String clientele) {
		this.clientele = clientele;
	}

	
	
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getSubId() {
		return subId;
	}

	public void setSubId(int subId) {
		this.subId = subId;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getPrimary_language() {
		return primary_language;
	}

	public void setPrimary_language(String primary_language) {
		this.primary_language = primary_language;
	}

	public String getScheduled_SC_frequency() {
		return scheduled_SC_frequency;
	}

	public void setScheduled_SC_frequency(String scheduled_SC_frequency) {
		this.scheduled_SC_frequency = scheduled_SC_frequency;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getSc() {
		return sc;
	}

	public void setSc(String sc) {
		this.sc = sc;
	}

	public String getNewsletter() {
		return newsletter;
	}

	public void setNewsletter(String newsletter) {
		this.newsletter = newsletter;
	}

	public String getIncludes() {
		return includes;
	}

	public void setIncludes(String includes) {
		this.includes = includes;
	}

	public String getAccess() {
		return Access;
	}

	public void setAccess(String access) {
		Access = access;
	}

	public String getScheduled_NL_frequency() {
		return scheduled_NL_frequency;
	}

	public void setScheduled_NL_frequency(String scheduled_NL_frequency) {
		this.scheduled_NL_frequency = scheduled_NL_frequency;
	}
	

}
