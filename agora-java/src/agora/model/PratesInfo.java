package agora.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "pratesinfo")
public class PratesInfo implements java.io.Serializable {
	public Integer id;
	public String prates_Yname;
	public String W;
	public String H;
	public String oneX;
	public String threeX;
	public String sixX;
	public String twelveX;
	public String eighteenX;
	public String twentyFourX;
	public String thirtySixX;
	public String metallic;
	public String standard;
	public String matched;
	public String four_color;
	public Subscription subscription;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@OneToOne
	@JoinColumn(name = "subscription_id")
	public Subscription getSubscription() {
		return subscription;
	}

	public void setSubscription(Subscription subscription) {
		this.subscription = subscription;
	}

	public String getPrates_Yname() {
		return prates_Yname;
	}

	public void setPrates_Yname(String prates_Yname) {
		this.prates_Yname = prates_Yname;
	}

	public String getW() {
		return W;
	}

	public void setW(String w) {
		W = w;
	}

	public String getH() {
		return H;
	}

	public void setH(String h) {
		H = h;
	}

	public String getOneX() {
		return oneX;
	}

	public void setOneX(String oneX) {
		this.oneX = oneX;
	}

	public String getThreeX() {
		return threeX;
	}

	public void setThreeX(String threeX) {
		this.threeX = threeX;
	}

	public String getSixX() {
		return sixX;
	}

	public void setSixX(String sixX) {
		this.sixX = sixX;
	}

	public String getTwelveX() {
		return twelveX;
	}

	public void setTwelveX(String twelveX) {
		this.twelveX = twelveX;
	}

	public String getEighteenX() {
		return eighteenX;
	}

	public void setEighteenX(String eighteenX) {
		this.eighteenX = eighteenX;
	}

	public String getTwentyFourX() {
		return twentyFourX;
	}

	public void setTwentyFourX(String twentyFourX) {
		this.twentyFourX = twentyFourX;
	}

	public String getThirtySixX() {
		return thirtySixX;
	}

	public void setThirtySixX(String thirtySixX) {
		this.thirtySixX = thirtySixX;
	}

	public String getMetallic() {
		return metallic;
	}

	public void setMetallic(String metallic) {
		this.metallic = metallic;
	}

	public String getStandard() {
		return standard;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}

	public String getMatched() {
		return matched;
	}

	public void setMatched(String matched) {
		this.matched = matched;
	}

	public String getFour_color() {
		return four_color;
	}

	public void setFour_color(String four_color) {
		this.four_color = four_color;
	}

}
