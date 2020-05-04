package ua.nure.razdobudkina.SummaryTask4.web.command;

import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

/**
 * Holder for all commands.<br/>
 * 
 * @author A.Razdobudkina
 * 
 */
public class CommandContainer {
	
	private static final Logger LOG = Logger.getLogger(CommandContainer.class);
	
	private static Map<String, Command> commands = new TreeMap<String, Command>();
	
	static {
		// common commands
		commands.put("login", new LoginCommand());
		commands.put("logout", new LogoutCommand());
		commands.put("viewSettings", new ViewSettingsCommand());
		commands.put("noCommand", new NoCommand());
		commands.put("listCourses", new CommandListCourses());	
		commands.put("myAccount", new CommandMyAccount());
		commands.put("changeLanguage", new CommandChangeLanguage());
		
		// student commands
		commands.put("RegisterCourse", new CommandRegisterCourse(true));
		commands.put("listCoursesForStudent", new CommandCoursesByStudent());
		
		// teacher commands
		commands.put("openJournal", new CommandOpenJournal());
		commands.put("saveJournal", new CommandSaveEntryInJournal(true));
		
		// admin commands
		commands.put("editCourse", new CommandEditCourse());
		commands.put("acceptEditCourse", new CommandAcceptEditCourse(true));
		commands.put("deleteCourse", new CommandDeleteCourse(true));
		commands.put("addCourse", new CommandEditCourse());
		commands.put("listUsers", new CommandListUsers());
		commands.put("changeStatusStudent", new CommandChangeStatusStudent(true));
		commands.put("addTeacher", new CommandEditTeacher());
		commands.put("saveTeacher", new CommandSaveTeacher(true));
		
		
		LOG.debug("Command container was successfully initialized");
		LOG.trace("Number of commands --> " + commands.size());
	}


	public static Command get(String commandName) {
		if (commandName == null || !commands.containsKey(commandName)) {
			LOG.trace("Command not found, name --> " + commandName);
			return commands.get("noCommand"); 
		}
		
		return commands.get(commandName);
	}
	
}