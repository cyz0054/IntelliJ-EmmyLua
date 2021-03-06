/*
 * Copyright (c) 2017. tangzx(love.tangzx@qq.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tang.intellij.lua.stubs.index;

import com.intellij.openapi.project.Project;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.stubs.StringStubIndexExtension;
import com.intellij.psi.stubs.StubIndex;
import com.intellij.psi.stubs.StubIndexKey;
import com.tang.intellij.lua.lang.LuaLanguage;
import com.tang.intellij.lua.psi.LuaClassMethod;
import com.tang.intellij.lua.search.SearchContext;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 *
 * Created by tangzx on 2016/12/4.
 */
public class LuaClassMethodIndex extends StringStubIndexExtension<LuaClassMethod> {
    public static final StubIndexKey<String, LuaClassMethod> KEY = StubIndexKey.createIndexKey("lua.index.class.method");

    private static final LuaClassMethodIndex INSTANCE = new LuaClassMethodIndex();

    public static LuaClassMethodIndex getInstance() {
        return INSTANCE;
    }

    @Override
    public int getVersion() { return LuaLanguage.INDEX_VERSION;}

    @NotNull
    @Override
    public StubIndexKey<String, LuaClassMethod> getKey() {
        return KEY;
    }

    @Override
    public Collection<LuaClassMethod> get(@NotNull String s, @NotNull Project project, @NotNull GlobalSearchScope scope) {
        return StubIndex.getElements(KEY, s, project, scope, LuaClassMethod.class);
    }

    public static Collection<LuaClassMethod> findStaticMethods(@NotNull String className, @NotNull SearchContext context) {
        String key = className + ".static";
        return INSTANCE.get(key, context.getProject(), context.getScope());
    }

    public static LuaClassMethod findStaticMethod(@NotNull String className, @NotNull String methodName, @NotNull SearchContext context) {
        if (context.isDumb())
            return null;

        Collection<LuaClassMethod> collection = INSTANCE.get(className + ".static." + methodName, context.getProject(), context.getScope());
        if (collection.isEmpty())
            return null;
        else
            return collection.iterator().next();
    }

    public static LuaClassMethod findMethodWithName(@NotNull String className, @NotNull String methodName, @NotNull SearchContext context) {
        if (context.isDumb())
            return null;

        Collection<LuaClassMethod> collection = INSTANCE.get(className, context.getProject(), context.getScope());
        for (LuaClassMethod methodDef : collection) {
            if (methodName.equals(methodDef.getName())) {
                return methodDef;
            }
        }
        return null;
    }
}
