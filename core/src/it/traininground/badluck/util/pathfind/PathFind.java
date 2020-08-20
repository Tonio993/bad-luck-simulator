package it.traininground.badluck.util.pathfind;

import java.util.List;

public interface PathFind<T> {
	
	public List<T> findPath(T from, T to);

}
