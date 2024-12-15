package org.lpc.s4_code_generation;

import org.bytedeco.javacpp.*;
import org.bytedeco.llvm.LLVM.*;
import org.lpc.s2_parsing.ast.FunctionDeclarationNode;

import static org.bytedeco.llvm.global.LLVM.*;

public class LLVMCodeGenerator {

    LLVMModuleRef module;
    LLVMBuilderRef builder;

    public LLVMCodeGenerator() {
        LLVMInitializeNativeTarget();
        LLVMInitializeNativeAsmPrinter();
        LLVMInitializeNativeAsmParser();

        module = LLVMModuleCreateWithName("testModule");
    }
}
