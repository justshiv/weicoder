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
 * "QName" type.
 * 
 * type of the value object is {@link QnameValueType}.
 * See http://www.w3.org/TR/xmlschema-2/#QName for the spec.
 * 
 * @author <a href="mailto:kohsuke.kawaguchi@eng.sun.com">Kohsuke KAWAGUCHI</a>
 */
public class QnameType extends BuiltinAtomicType implements Discrete {
    public static final QnameType theInstance = new QnameType();
    private QnameType() { super("QName"); }
    
    final public XSDatatype getBaseType() {
        return SimpleURType.theInstance;
    }
    
    /**
     * QName type always returns true. That is, QName is a context-dependent type.
     */
    public boolean isContextDependent() {
        return true;
    }
    
    protected boolean checkFormat( String value, ValidationContext context ) {
        // [6] QName ::= (Prefix ':')? LocalPart
        // [7] Prefix ::= NCName
        // [8] LocalPart ::= NCName

        final int first = value.indexOf(':');

        // no Prefix, only check LocalPart
        if(first <= 0)        return XmlNames.isUnqualifiedName(value);

        // Prefix exists, check everything
        final int    last = value.lastIndexOf(':');
        if (last != first)    return false;

        final String prefix = value.substring (0, first);
        return XmlNames.isUnqualifiedName(prefix)
            && XmlNames.isUnqualifiedName(value.substring (first + 1))
            && context.resolveNamespacePrefix(prefix)!=null;
    }
    
    public Object _createValue( String value, ValidationContext context ) {
        String uri,localPart;
        // [6] QName ::= (Prefix ':')? LocalPart
        // [7] Prefix ::= NCName
        // [8] LocalPart ::= NCName

        final int first = value.indexOf(':');

        if(first <= 0)
        {// no Prefix, only check LocalPart
            if(!XmlNames.isUnqualifiedName(value))    return null;
            uri = context.resolveNamespacePrefix("");
            localPart = value;
        } else {
            // Prefix exists, check everything
            final int    last = value.lastIndexOf (':');
            if (last != first)    return null;
            
            final String prefix = value.substring(0, first);
            localPart = value.substring(first + 1);
            
            if(!XmlNames.isUnqualifiedName(prefix)
            || !XmlNames.isUnqualifiedName(localPart) )
                return null;
            
            uri = context.resolveNamespacePrefix(prefix);
        }
        
        if(uri==null)    return null;
        
        return new QnameValueType(uri,localPart);
    }
    
    public final int isFacetApplicable( String facetName ) {
        if( facetName.equals(FACET_PATTERN)
        ||    facetName.equals(FACET_ENUMERATION)
        ||  facetName.equals(FACET_WHITESPACE)
        ||    facetName.equals(FACET_LENGTH)
        ||    facetName.equals(FACET_MAXLENGTH)
        ||    facetName.equals(FACET_MINLENGTH)
        )
            return APPLICABLE;
        else
            return NOT_ALLOWED;
    }
    
    public final int countLength( Object value ) {
        QnameValueType v = (QnameValueType)value;
        
        // the spec does not define what is the unit of length.
        // TODO: check the update of the spec and modify this if necessary.
        return    UnicodeUtil.countLength( v.namespaceURI )+
                UnicodeUtil.countLength( v.localPart );
    }

    
    public String convertToLexicalValue( Object o, SerializationContext context ) {
        if(!( o instanceof QnameValueType ))
            throw new UnsupportedOperationException();
        
        QnameValueType v = (QnameValueType)o;
        
        return serialize(v.namespaceURI,v.localPart,context);
    }

    public String serializeJavaObject( Object value, SerializationContext context ) {
        if(!(value instanceof String[]))    throw new IllegalArgumentException();
        String[] input = (String[])value;
        if( input.length!=2 )                throw new IllegalArgumentException();
        
        return serialize(input[0],input[1],context);
    }
    
    private String serialize( String uri, String local, SerializationContext context ) {
        String prefix = context.getNamespacePrefix(uri);
        if(prefix==null)    return local;
        else                return prefix+":"+local;
    }
    
    public Object _createJavaObject( String literal, ValidationContext context ) {
        QnameValueType v = (QnameValueType)createValue(literal,context);
        if(v==null)        return null;
        // return String[2]
        else            return new String[]{v.namespaceURI,v.localPart};
    }

    public Class<String[]> getJavaObjectType() {
        return String[].class;
    }

    // serialization support
    private static final long serialVersionUID = 1;    
}
