package entity;

interface Entity {
	public void OnInitialize(Scene hierarchyScene);
	public void OnUpdate(double timeStep);
	public void OnPhysicsUpdate(double timeStep);
	public void OnDraw();
}
