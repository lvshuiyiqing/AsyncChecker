package ac.processor;

import soot.Scene;
import soot.jimple.AssignStmt;
import soot.jimple.NewExpr;
import ac.constant.AsyncClassSignature;
import ac.entity.AsyncTaskRefObject;
import ac.entity.AsyncTypeState;
import ac.record.AsyncErrorRecord;
import androlic.entity.ContextMessage;
import androlic.entity.GlobalMessage;
import androlic.entity.value.heap.ref.NewRefHeapObject;
import androlic.execution.processor.rightop.expr.newex.INewExprProcessor;
import androlic.util.ClassInheritanceProcess;

public class AsyncNewExprProcessor implements INewExprProcessor {

	private static AsyncNewExprProcessor processor;

	private AsyncNewExprProcessor() {
	}

	public static AsyncNewExprProcessor v() {
		if (processor == null) {
			processor = new AsyncNewExprProcessor();
		}
		return processor;
	}
	
	public NewRefHeapObject getNewHeapObject(AssignStmt assignStmt, NewExpr newExpr,
			ContextMessage context, GlobalMessage globalMessage) {
		if (ClassInheritanceProcess.isInheritedFromGivenClass(newExpr.getBaseType(), Scene.v().getRefTypeUnsafe(AsyncClassSignature.ASYNC_TASK))) {
			AsyncTaskRefObject theObject = new AsyncTaskRefObject(assignStmt, globalMessage);
			AsyncTypeState newState = new AsyncTypeState();
			newState.getExecutionUnitList().add(assignStmt);
			globalMessage.getObjectToTypeState().put(theObject, newState);
//			AsyncMain.rightInstanceSet.add(theObject);
//			AsyncMain.rightInstanceMap.put(theObject.getObjectKey(), theObject);
			AsyncErrorRecord.recordInstance(theObject);
			return theObject;
		}
		else {
			return null;
		}
	}

}
