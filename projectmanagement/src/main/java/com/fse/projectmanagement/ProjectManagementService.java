package com.fse.projectmanagement;

public interface ProjectManagementService {
	public boolean update(int id, String name);
	public Project read(int id);
	public boolean delete(int id);
	
	public boolean assignMember(int id, int mId);
	public boolean unassignMember(int id, int mId);
}
