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

import com.sun.msv.datatype.xsd.regex.RegExp;
import com.sun.msv.datatype.xsd.regex.RegExpFactory; 
import org.relaxng.datatype.ValidationContext;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Vector;
import java.text.ParseException;

/**
 * "pattern" facet validator
 * 
 * "pattern" is a constraint facet which is applied against lexical space.
 * See http://www.w3.org/TR/xmlschema-2/#dt-pattern for the spec
 * 
 * @author <a href="mailto:kohsuke.kawaguchi@eng.sun.com">Kohsuke KAWAGUCHI</a>
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public final class PatternFacet extends DataTypeWithLexicalConstraintFacet {
    
    /**
     * actual object that performs regular expression validation.
     * one of the item has to match.
     *
     * Don't serialize so that we won't have hard-coded dependency to
     * a particular regexp lib.
     */
    private transient RegExp[] exps;
    
    public RegExp[] getRegExps() { return exps; }
    
    /**
     * string representations of the above RegularExpressions.
     * this representation is usually human friendly than
     * the one generated by RegularExpression.toString method.
     */
    final public String[] patterns;

    
    
    public PatternFacet( String nsUri, String typeName, XSDatatypeImpl baseType, TypeIncubator facets )
         {
        super( nsUri, typeName, baseType, FACET_PATTERN, facets.isFixed(FACET_PATTERN) );
        
        
        // TODO : am I supposed to implement my own regexp validator?
        // at this time, I use Xerces' one.
        
        Vector regExps = facets.getVector(FACET_PATTERN);
        patterns = (String[]) regExps.toArray(new String[regExps.size()]);
        
        try {
            compileRegExps();
        } catch( ParseException pe ) {
            // in case regularExpression is not a correct pattern
            throw new RuntimeException( localize( ERR_PARSE_ERROR,
                pe.getMessage() ) );
        }
    }
    
    /** Compiles all the regular expressions. */
    private void compileRegExps() throws ParseException {
        exps = new RegExp[patterns.length];
        RegExpFactory factory = RegExpFactory.createFactory();
        for(int i=0;i<exps.length;i++)
            exps[i] = factory.compile(patterns[i]);
        
        // loosened facet check is almost impossible for pattern facet.
        // ignore it for now.
    }
    
    protected void diagnoseByFacet(String content, ValidationContext context)  {
        if( checkLexicalConstraint(content) )    return;
        
        if( exps.length==1 )
            throw new RuntimeException( 
                localize(ERR_PATTERN_1,patterns[0]) );
        else
            throw new RuntimeException( 
                localize(ERR_PATTERN_MANY) );
    }
    
    protected final boolean checkLexicalConstraint( String literal ) {
        // makes sure that at least one of the patterns is satisfied.
        
        // regexp can be not thread-safe. Make sure only one thread uses it
        // at any given time.
        synchronized(this) {
            for( int i=0; i<exps.length; i++ )
                if(exps[i].matches(literal))
                    return true;
        }
        // otherwise fail
        return false;
    }
    
    
    private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
        stream.defaultReadObject();

        try {
            compileRegExps();
        } catch (ParseException e) {
            throw new IOException(e.getMessage());
        }
    }
    

    // serialization support
    private static final long serialVersionUID = 1;
}