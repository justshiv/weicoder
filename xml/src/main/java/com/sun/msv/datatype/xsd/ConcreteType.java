/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2001-2013 Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and  use in  source and binary  forms, with  or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * - Redistributions  of  source code  must  retain  the above  copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * - Redistribution  in binary  form must  reproduct the  above copyright
 *   notice, this list of conditions  and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * Neither  the  name   of  Sun  Microsystems,  Inc.  or   the  names  of
 * contributors may be  used to endorse or promote  products derived from
 * this software without specific prior written permission.
 * 
 * This software is provided "AS IS," without a warranty of any kind. ALL
 * EXPRESS  OR   IMPLIED  CONDITIONS,  REPRESENTATIONS   AND  WARRANTIES,
 * INCLUDING  ANY  IMPLIED WARRANTY  OF  MERCHANTABILITY,  FITNESS FOR  A
 * PARTICULAR PURPOSE  OR NON-INFRINGEMENT, ARE HEREBY  EXCLUDED. SUN AND
 * ITS  LICENSORS SHALL  NOT BE  LIABLE  FOR ANY  DAMAGES OR  LIABILITIES
 * SUFFERED BY LICENSEE  AS A RESULT OF OR  RELATING TO USE, MODIFICATION
 * OR DISTRIBUTION OF  THE SOFTWARE OR ITS DERIVATIVES.  IN NO EVENT WILL
 * SUN OR ITS  LICENSORS BE LIABLE FOR ANY LOST  REVENUE, PROFIT OR DATA,
 * OR  FOR  DIRECT,   INDIRECT,  SPECIAL,  CONSEQUENTIAL,  INCIDENTAL  OR
 * PUNITIVE  DAMAGES, HOWEVER  CAUSED  AND REGARDLESS  OF  THE THEORY  OF
 * LIABILITY, ARISING  OUT OF  THE USE OF  OR INABILITY TO  USE SOFTWARE,
 * EVEN IF SUN HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 */

package com.sun.msv.datatype.xsd;

import com.sun.msv.datatype.SerializationContext; 
import org.relaxng.datatype.ValidationContext;

/**
 * base class for types that union/list/atomic.
 * 
 * @author <a href="mailto:kohsuke.kawaguchi@eng.sun.com">Kohsuke KAWAGUCHI</a>
 */
public abstract class ConcreteType extends XSDatatypeImpl {
    
    protected ConcreteType( String nsUri, String typeName, WhiteSpaceProcessor whiteSpace ) {
        super( nsUri, typeName, whiteSpace );
    }
    
    protected ConcreteType( String nsUri, String typeName ) {
        this( nsUri, typeName, WhiteSpaceProcessor.theCollapse );
    }
    
    final public ConcreteType getConcreteType() {
        return this;
    }
    
    public boolean isFinal( int derivationType ) {
        // allow derivation by default.
        return false;
    }

    // default implementation for concrete type. somewhat shabby.
    protected void _checkValid(String content, ValidationContext context) {
        if(checkFormat(content,context))    return;
        
        throw new RuntimeException(localize(ERR_INAPPROPRIATE_FOR_TYPE, content, getName()) );
    }
    
//
// DatabindableDatatype implementation
//===========================================
// The default implementation yields to the createValue method and
// the convertToLexicalValue method. If a derived class overrides the
// createJavaObject method, then it must also override the serializeJavaObject method.
//
    public Object _createJavaObject( String literal, ValidationContext context ) {
        return _createValue(literal,context);
    }
    public String serializeJavaObject( Object value, SerializationContext context ) {
        String literal = convertToLexicalValue( value, context );
        if(!isValid( literal, serializedValueChecker ))
            return null;
        else
            return literal;
    }

    // serialization support
    private static final long serialVersionUID = 1;    
}