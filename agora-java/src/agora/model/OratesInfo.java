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
@Table(name = "oratesinfo")
public class OratesInfo implements java.io.Serializable {
	public Integer id;
	public String Y_names;
	public String LeaderBoard;
	public String Banner;
	public String Skyscraper;
	public String Sponsored_Links_ROS;
	public String Interstitial_Pop_Up;
	public String Page_Peel;
	public String Page_Push;
	public String Video_Ad;
	public String Logo;

	public String Box_Ad;
	public String Sponsor_Posts_per_post;
	public String Small_Box;

	public String Marketplace;
	public String Custom;
	public String Text_Ads;
	public String Featured_Products;
	public String Text_65_Words;
	public Subscription subscription;
	public String Button;

	public String large_rectangle;
	public String Box;
	public String Rectangle;
	public String e_solution_Broadcast;
	public String Footer;
	public String Top_position;
	public String Button_footer;
	public String Showcase;
	public String Banner_Footer;
	public String Featured_Profile_Pdt;
	public String e_solution_Broadcast_Footer;
	public String Hosting;
	public String Exhibit_Hall;
	public String Pdt_List;
	public String Insert_Footer;

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

	public String getY_names() {
		return Y_names;
	}

	public void setY_names(String y_names) {
		Y_names = y_names;
	}

	public String getLeaderBoard() {
		return LeaderBoard;
	}

	public void setLeaderBoard(String leaderBoard) {
		LeaderBoard = leaderBoard;
	}

	public String getBanner() {
		return Banner;
	}

	public void setBanner(String banner) {
		Banner = banner;
	}

	public String getSkyscraper() {
		return Skyscraper;
	}

	public void setSkyscraper(String skyscraper) {
		Skyscraper = skyscraper;
	}

	public String getSponsored_Links_ROS() {
		return Sponsored_Links_ROS;
	}

	public void setSponsored_Links_ROS(String sponsored_Links_ROS) {
		Sponsored_Links_ROS = sponsored_Links_ROS;
	}

	public String getInterstitial_Pop_Up() {
		return Interstitial_Pop_Up;
	}

	public void setInterstitial_Pop_Up(String interstitial_Pop_Up) {
		Interstitial_Pop_Up = interstitial_Pop_Up;
	}

	public String getPage_Peel() {
		return Page_Peel;
	}

	public void setPage_Peel(String page_Peel) {
		Page_Peel = page_Peel;
	}

	public String getPage_Push() {
		return Page_Push;
	}

	public void setPage_Push(String page_Push) {
		Page_Push = page_Push;
	}

	public String getVideo_Ad() {
		return Video_Ad;
	}

	public void setVideo_Ad(String video_Ad) {
		Video_Ad = video_Ad;
	}

	public String getLogo() {
		return Logo;
	}

	public void setLogo(String logo) {
		Logo = logo;
	}

	public String getBox_Ad() {
		return Box_Ad;
	}

	public void setBox_Ad(String box_Ad) {
		Box_Ad = box_Ad;
	}

	public String getSponsor_Posts_per_post() {
		return Sponsor_Posts_per_post;
	}

	public void setSponsor_Posts_per_post(String sponsor_Posts_per_post) {
		Sponsor_Posts_per_post = sponsor_Posts_per_post;
	}

	public String getSmall_Box() {
		return Small_Box;
	}

	public void setSmall_Box(String small_Box) {
		Small_Box = small_Box;
	}

	public String getMarketplace() {
		return Marketplace;
	}

	public void setMarketplace(String marketplace) {
		Marketplace = marketplace;
	}

	public String getCustom() {
		return Custom;
	}

	public void setCustom(String custom) {
		Custom = custom;
	}

	public String getText_Ads() {
		return Text_Ads;
	}

	public void setText_Ads(String text_Ads) {
		Text_Ads = text_Ads;
	}

	public String getFeatured_Products() {
		return Featured_Products;
	}

	public void setFeatured_Products(String featured_Products) {
		Featured_Products = featured_Products;
	}

	public String getText_65_Words() {
		return Text_65_Words;
	}

	public void setText_65_Words(String text_65_Words) {
		Text_65_Words = text_65_Words;
	}

	public String getButton() {
		return Button;
	}

	public void setButton(String button) {
		Button = button;
	}

	public String getLarge_rectangle() {
		return large_rectangle;
	}

	public void setLarge_rectangle(String large_rectangle) {
		this.large_rectangle = large_rectangle;
	}

	public String getBox() {
		return Box;
	}

	public void setBox(String box) {
		Box = box;
	}

	public String getRectangle() {
		return Rectangle;
	}

	public void setRectangle(String rectangle) {
		Rectangle = rectangle;
	}

	public String getE_solution_Broadcast() {
		return e_solution_Broadcast;
	}

	public void setE_solution_Broadcast(String e_solution_Broadcast) {
		this.e_solution_Broadcast = e_solution_Broadcast;
	}

	public String getFooter() {
		return Footer;
	}

	public void setFooter(String footer) {
		Footer = footer;
	}

	public String getTop_position() {
		return Top_position;
	}

	public void setTop_position(String top_position) {
		Top_position = top_position;
	}

	public String getButton_footer() {
		return Button_footer;
	}

	public void setButton_footer(String button_footer) {
		Button_footer = button_footer;
	}

	public String getShowcase() {
		return Showcase;
	}

	public void setShowcase(String showcase) {
		Showcase = showcase;
	}

	public String getBanner_Footer() {
		return Banner_Footer;
	}

	public void setBanner_Footer(String banner_Footer) {
		Banner_Footer = banner_Footer;
	}

	public String getFeatured_Profile_Pdt() {
		return Featured_Profile_Pdt;
	}

	public void setFeatured_Profile_Pdt(String featured_Profile_Pdt) {
		Featured_Profile_Pdt = featured_Profile_Pdt;
	}

	public String getE_solution_Broadcast_Footer() {
		return e_solution_Broadcast_Footer;
	}

	public void setE_solution_Broadcast_Footer(
			String e_solution_Broadcast_Footer) {
		this.e_solution_Broadcast_Footer = e_solution_Broadcast_Footer;
	}

	public String getHosting() {
		return Hosting;
	}

	public void setHosting(String hosting) {
		Hosting = hosting;
	}

	public String getExhibit_Hall() {
		return Exhibit_Hall;
	}

	public void setExhibit_Hall(String exhibit_Hall) {
		Exhibit_Hall = exhibit_Hall;
	}

	public String getPdt_List() {
		return Pdt_List;
	}

	public void setPdt_List(String pdt_List) {
		Pdt_List = pdt_List;
	}

	public String getInsert_Footer() {
		return Insert_Footer;
	}

	public void setInsert_Footer(String insert_Footer) {
		Insert_Footer = insert_Footer;
	}

	

}
