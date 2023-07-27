package by.teachmeskills.project.actuator;

import by.teachmeskills.project.enums.PagesPathEnum;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import java.io.FileReader;
import java.io.IOException;
@Component
@Endpoint(id = "project")
public class ProjectStatisticEndpoint {
    private final static Logger logger = LoggerFactory.getLogger(ProjectStatisticEndpoint.class);
    @ReadOperation
    public ModelAndView getProjectInfo(){
        ModelMap modelMap = new ModelMap();
        MavenXpp3Reader reader = new MavenXpp3Reader();
        Model model;
        try {
            model = reader.read(new FileReader("pom.xml"));
            modelMap.addAttribute("projectFullId", model.getId());
            modelMap.addAttribute("groupId", model.getGroupId());
            modelMap.addAttribute("artifactId", model.getArtifactId());
            modelMap.addAttribute("version", model.getVersion());
        } catch (IOException | XmlPullParserException e) {
            logger.warn("Cannot return statistics because of exception" + e.getMessage());
        }
        return new ModelAndView(PagesPathEnum.PROJECT_INFO_PAGE.getPath(),modelMap);
    }
}
