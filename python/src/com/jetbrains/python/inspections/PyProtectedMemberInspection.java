package com.jetbrains.python.inspections;

import com.intellij.codeInspection.LocalInspectionToolSession;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiReference;
import com.intellij.psi.util.PsiTreeUtil;
import com.jetbrains.python.PyBundle;
import com.jetbrains.python.psi.PyClass;
import com.jetbrains.python.psi.PyExpression;
import com.jetbrains.python.psi.PyReferenceExpression;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * User: ktisha
 *
 * Inspection to detect situations, where
 * protected member (i.e. class member with a name beginning with an underscore)
 * is access outside the class or a descendant of the class where it's defined.
 */
public class PyProtectedMemberInspection extends PyInspection {
  @Nls
  @NotNull
  @Override
  public String getDisplayName() {
    return PyBundle.message("INSP.NAME.protected.member.access");
  }

  @NotNull
  @Override
  public PsiElementVisitor buildVisitor(@NotNull ProblemsHolder holder,
                                        boolean isOnTheFly,
                                        @NotNull LocalInspectionToolSession session) {
    return new Visitor(holder, session);
  }


  private static class Visitor extends PyInspectionVisitor {
    public Visitor(@Nullable ProblemsHolder holder, @NotNull LocalInspectionToolSession session) {
      super(holder, session);
    }

    @Override
    public void visitPyReferenceExpression(PyReferenceExpression node) {
      final PyExpression qualifier = node.getQualifier();
      if (qualifier == null) return;
      final String name = node.getName();
      if (name != null && name.startsWith("_")) {
        final PyClass parentClass = PsiTreeUtil.getParentOfType(node, PyClass.class);
        if (parentClass != null) {
          final PsiReference reference = node.getReference();
          final PsiElement resolvedExpression = reference.resolve();
          final PyClass resolvedClass = PsiTreeUtil.getParentOfType(resolvedExpression, PyClass.class);
          if (parentClass.isSubclass(resolvedClass))
            return;
        }
        registerProblem(node, PyBundle.message("INSP.protected.member.$0.access", name));
      }
    }

  }
}
