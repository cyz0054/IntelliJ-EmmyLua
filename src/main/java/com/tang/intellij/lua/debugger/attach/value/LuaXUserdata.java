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

package com.tang.intellij.lua.debugger.attach.value;

import com.intellij.icons.AllIcons;
import com.intellij.xdebugger.frame.XValueNode;
import com.intellij.xdebugger.frame.XValuePlace;
import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * user data
 * Created by Administrator on 2017/6/13.
 */
public class LuaXUserdata extends LuaXTable {
    private String type = "userdata";
    private String data = "unknown";

    @Override
    public void doParse(Node node) {
        super.doParse(node);
        NodeList childNodes = node.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node item = childNodes.item(i);
            switch (item.getNodeName()) {
                case "type":
                    type = item.getTextContent();
                    break;
                case "data":
                    data = item.getTextContent();
                    break;
            }
        }
    }

    @Override
    public void computePresentation(@NotNull XValueNode xValueNode, @NotNull XValuePlace xValuePlace) {
        xValueNode.setPresentation(AllIcons.Json.Object, type, data, true);
    }

    @Override
    public String toKeyString() {
        return data;
    }
}
