package global;

public enum TaskType {

	Default,
	Apple_Notification,//using apple's notification system
	Browser_Launch,//launching browser with specified webpage
	Manga_Download,//starts/resumes manga download task
	Page_Download,//downloads a page in a specified format
	Start_Workflow,//Starts automator workflow in the case that there's not other enum type for it
	Anime_Download//downloads an anime/checks to see if new and download
}
