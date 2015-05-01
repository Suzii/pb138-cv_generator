<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : xml-to-tex.xsl
    Created on : Piatok, 2015, mája 1, 9:10
    Author     : Jozef Živčic
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="text"/>

    <!-- TODO customize transformation rules 
         syntax recommendation http://www.w3.org/TR/xslt 
    -->
    <xsl:template match="/curriculum-vitae">
        \documentclass[paper=a4,fontsize=11pt]{scrartcl}					
        \usepackage[slovak]{babel}
        \usepackage[IL2]{fontenc}
        \usepackage[utf8]{inputenc}
        \usepackage[protrusion=true,expansion=true]{microtype}
        \usepackage{graphicx}                   
        \usepackage[svgnames]{xcolor}            
        \usepackage{geometry}
                \textheight=700px
        \usepackage{url}

        \frenchspacing              
        \pagestyle{empty} 

        \usepackage{sectsty}

        \sectionfont{%			            
                \usefont{OT1}{phv}{b}{n}%
                \sectionrule{0pt}{0pt}{-5pt}{3pt}}

        \newlength{\spacebox}
        \settowidth{\spacebox}{8888888888}	
        \newcommand{\sepspace}{\vspace*{1em}}

        \newcommand{\MySlogan}[1]{
                        \Huge \usefont{OT1}{phv}{b}{n}
                        \begin{center}
                        #1
                        \end{center}
                        \par \normalsize \normalfont}

        \newcommand{\NewPart}[1]{\section*{\uppercase{#1}}}

        \newcommand{\PersonalEntry}[2]{
                        \noindent\hangindent=2em\hangafter=0
                        \parbox{\spacebox}{        
                        \textit{#1}}		       
                        \hspace{1.5em} #2 \par}    

        \newcommand{\SkillsEntry}[2]{      
                        \noindent\hangindent=2em\hangafter=0 
                        \parbox{\spacebox}{        
                        \textit{#1}}			   
                        \hspace{1.5em} #2 \par}   	

        \newcommand{\EducationEntry}[4]{
                        \noindent \textbf{#1} \hfill     
                        \colorbox{Black}{%
                                \parbox{8em}{%
                                \hfill\color{White}#2}} \par  
                        \noindent \textit{#3} \par       
                        \noindent\hangindent=2em\hangafter=0 \small #4 
                        \normalsize \par}
        \newcommand{\PlainText}[1]{\noindent\hangindent=2em\hangafter=0\par #1}
        \begin{document}

        \MySlogan{Curriculum Vitae}

        \sepspace
        
        \NewPart{Personal details}{}
        <xsl:apply-templates select="personal-details" />
        \NewPart{Education}{}
        <xsl:apply-templates select="education/edu" />
        <xsl:if test="employment/emp">
            \NewPart{Work Experience}{}
            <xsl:apply-templates select="employment/emp" />
        </xsl:if>
        <xsl:if test="certificates/cert">
            \NewPart{Certificates}{}
            <xsl:apply-templates select="certificates/cert" />
        </xsl:if>
        \NewPart{Language Skills}{}
        <xsl:apply-templates select="language-skills/lang" />
        <xsl:if test="computer-skills/skill">
            \NewPart{Computer Skills}{}
            <xsl:apply-templates select="computer-skills/skill" />
        </xsl:if>
        <xsl:if test="driving-licence/class">
            \NewPart{Driving licence}{}
            <xsl:apply-templates select="driving-licence/class"/>
        </xsl:if>
        <xsl:if test="characteristic">
            \NewPart{Characteristics}{}
            <xsl:apply-templates select="characteristic" />
        </xsl:if>
        <xsl:if test="hobbies">
            \NewPart{Hobbies}{}
            <xsl:apply-templates select="hobbies" />
        </xsl:if>
        \end{document}
    </xsl:template>
    <!-- Personal details -->
    <xsl:template match="/curriculum-vitae/personal-details">
        \PersonalEntry{Name}{<xsl:value-of select="given-names" />}
        \PersonalEntry{Surname}{<xsl:value-of select="surname" />}
        <xsl:apply-templates select="address" />
        <!-- test if phones are present -->
        <xsl:if test="phones">
            <xsl:apply-templates select="phones" />
        </xsl:if>
        <!-- emails -->
        <xsl:if test="emails">
            <xsl:apply-templates select="emails" />
        </xsl:if>
        <xsl:if test="social">
            <xsl:apply-templates select="social" />
        </xsl:if>
    </xsl:template>
    
    <!-- address -->
    <xsl:template match="/curriculum-vitae/personal-details/address" >
        \PersonalEntry{Address}{<xsl:value-of select="street" />
            <xsl:text>, </xsl:text>
            <xsl:value-of select="number" />
            <xsl:text> </xsl:text>
            <xsl:value-of select="city" />
        }
        \PersonalEntry{}{<xsl:value-of select="postal-code" />}
        \PersonalEntry{Country}{<xsl:value-of select="country" />}
        <!--test if state is present-->
        <xsl:if test="state">
            \PersonalEntry{State}{<xsl:value-of select="state" />}
        </xsl:if>
    </xsl:template>
    
    <!-- phones -->
    <xsl:template match="/curriculum-vitae/personal-details/phones" >
        <xsl:variable name="count" select="count(phone)" />
        <!-- if only one phone is given, the title is Phone, otherwise Phones-->
        <xsl:if test="$count = '1'">
            \PersonalEntry{Phone}{<xsl:value-of select="." />}
        </xsl:if>
        <xsl:if test="$count != '1'">
            <xsl:for-each select="phone" >
                <xsl:variable name="i" select="position()" />
                <xsl:choose>
                    <xsl:when test="$i = '1'">
                        \PersonalEntry{Phones}{<xsl:value-of select="." />}
                    </xsl:when>
                    <xsl:otherwise>
                        \PersonalEntry{}{<xsl:value-of select="." />}
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:for-each>
        </xsl:if>
    </xsl:template>
    
    <!--emails-->
    <xsl:template match="/curriculum-vitae/personal-details/emails">
        <xsl:variable name="count" select="count(email)" />
        <!-- if only one email address is given, the title is Email, otherwise Emails-->
        <xsl:if test="$count = '1'">
            \PersonalEntry{Email}{<xsl:value-of select="." />}
        </xsl:if>
        <xsl:if test="$count != '1'">
            <xsl:for-each select="email" >
                <xsl:variable name="i" select="position()" />
                <xsl:choose>
                    <xsl:when test="$i = '1'">
                        \PersonalEntry{Emails}{<xsl:value-of select="." />}
                    </xsl:when>
                    <xsl:otherwise>
                        \PersonalEntry{}{<xsl:value-of select="." />}
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:for-each>
        </xsl:if>
    </xsl:template>
    
    <!--social-->
    <xsl:template match="/curriculum-vitae/personal-details/social" >
        <xsl:variable name="count" select="count(site)" />
        <!-- if only one contact is given, the title is Other contact, otherwise Other Contacts-->
        <xsl:if test="$count = '1'">
            \PersonalEntry{My site}{<xsl:value-of select="." />}
        </xsl:if>
        <xsl:if test="$count != '1'">
            <xsl:for-each select="site" >
                <xsl:variable name="i" select="position()" />
                <xsl:choose>
                    <xsl:when test="$i = '1'">
                        \PersonalEntry{My sites}{<xsl:value-of select="." />}
                    </xsl:when>
                    <xsl:otherwise>
                        \PersonalEntry{}{<xsl:value-of select="." />}
                    </xsl:otherwise>
                </xsl:choose>
            </xsl:for-each>
        </xsl:if>
    </xsl:template>
    
    <!-- education -->
    <xsl:template match="/curriculum-vitae/education/edu">
        <xsl:variable name="to" select="./@to"/>
        <xsl:if test="$to = ''">
            \EducationEntry{<xsl:value-of select="." />}{<xsl:value-of select="./@from" /> - present}{}{}
        </xsl:if>
        <xsl:if test="$to != ''">
            \EducationEntry{<xsl:value-of select="." />}{<xsl:value-of select="./@from" /> - <xsl:value-of select="./@to" />}{}{}
        </xsl:if>
        \sepspace
    </xsl:template>
    <!--employment -->
    <xsl:template match="/curriculum-vitae/employment/emp">
        <xsl:variable name="to" select="./@to"/>
        <xsl:if test="$to = ''">
            \EducationEntry{<xsl:value-of select="position" />}{<xsl:value-of select="./@from" /> - present}{<xsl:value-of select="company" />}{<xsl:value-of select="note" />}
        </xsl:if>
        <xsl:if test="$to !=''">
            \EducationEntry{<xsl:value-of select="position" />}{<xsl:value-of select="./@from" /> - <xsl:value-of select="./@to" />}{<xsl:value-of select="company" />}{<xsl:value-of select="note" />}
        </xsl:if>
        \sepspace
    </xsl:template>
    
    <!--language skills-->
    <xsl:template match="/curriculum-vitae/language-skills/lang">
        \SkillsEntry{<xsl:value-of select="./@name" />}{<xsl:value-of select="level" />}
        <xsl:variable name="length" select="note/text()" />
        <xsl:if test="note/text() != ''">
            \SkillsEntry{}{<xsl:value-of select="note" />}
        </xsl:if>
    </xsl:template>
    
    <!--certificates-->
    <xsl:template match="/curriculum-vitae/certificates/cert">
        \EducationEntry{<xsl:value-of select="name" />}{<xsl:value-of select="./@year" />}{<xsl:value-of select="note" />}
        \sepspace
    </xsl:template>
    
    <!--computer skills-->
    <xsl:template match="/curriculum-vitae/computer-skills/skill">
        \SkillsEntry{<xsl:value-of select="name" />}{<xsl:value-of select="level" />}
        \SkillsEntry{}{<xsl:value-of select="note" />}
    </xsl:template>
    
    <!--driving licence-->
    <xsl:template match="/curriculum-vitae/driving-licence/class">
        \SkillsEntry{<xsl:value-of select="name" />}{<xsl:value-of select="note" />}
    </xsl:template>
    
    <!--characteristic-->
    <xsl:template match="/curriculum-vitae/characteristic">
        \PlainText{<xsl:value-of select="." />}
    </xsl:template>
    
    <!--hobbies-->
    <xsl:template match="/curriculum-vitae/hobbies">
        \PlainText{<xsl:value-of select="." />}
    </xsl:template>
</xsl:stylesheet>
