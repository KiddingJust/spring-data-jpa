package study.datajpa.repository;

public interface NestedClosedProjections {

    String getUsername();
    TeamInfo getTeam();

    //프로젝션인데 팀 이름
    interface TeamInfo {
        String getName();
    }
}
