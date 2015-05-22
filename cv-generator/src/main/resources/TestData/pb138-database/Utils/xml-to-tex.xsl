<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : xml-to-tex.xsl
    Created on : Friday, 2015, may 1, 9:10
    Author     : Jozef Zivcic
    Description:
        This file is used to transforming an XML in a predefined format. Format is
        given by file sample-cv.xml. An output is Curriculum Vitae
        stored as LaTeX document.
-->
<!--NOTE This file should be placed on filesystem path which does not contain any diacritic marks-->
<!---->
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="text"/>
    
    <!-- This parameter specifies cv output languate. Only en and sk values are available, en stands for english, sk for slovak -->
    <xsl:param name="cv-language" select="'en'"/>
    
    <!--Text file which contains definitions of output texts-->
    <!--This file should be placed in the same folder as xml-to-tex.xsl template -->
    <xsl:variable name="texts" select="document('texts.xml')" />
    
    <xsl:template match="/curriculum-vitae">
        
        <!--TeX config-->
        <xsl:call-template name="tex-style" />
        
        %At this point the TeX document begins.
        \begin{document}

        \MySlogan{<xsl:value-of select="$texts//language[@id = $cv-language]/curriculum-vitae" />}
        \sepspace
        
        <!--Personal details-->
        \NewPart{<xsl:value-of select="$texts//language[@id = $cv-language]/personal-details" />}{}
        <xsl:apply-templates select="personal-details" />
        
        <!--Education-->
        <xsl:if test="education/edu">
            \NewPart{<xsl:value-of select="$texts//language[@id = $cv-language]/education" />}{}
            <xsl:apply-templates select="education/edu" />
        </xsl:if>
        
        <!--Employment-->
        <xsl:if test="employment/emp">
            \NewPart{<xsl:value-of select="$texts//language[@id = $cv-language]/work-experience" />}{}
            <xsl:apply-templates select="employment/emp" />
        </xsl:if>
        
        <!--Certificates-->
        <xsl:if test="certificates/cert">
            \NewPart{<xsl:value-of select="$texts//language[@id = $cv-language]/certificates" />}{}
            <xsl:apply-templates select="certificates/cert" />
        </xsl:if>
        
        <!--Language skills-->
        <xsl:if test="language-skills/lang">
            \NewPart{<xsl:value-of select="$texts//language[@id = $cv-language]/language-skills" />}{}
            <xsl:apply-templates select="language-skills/lang" />
        </xsl:if>
        
        <!--Computer skills-->
        <xsl:if test="computer-skills/skill">
            \NewPart{<xsl:value-of select="$texts//language[@id = $cv-language]/computer-skills" />}{}
            <xsl:apply-templates select="computer-skills/skill" />
        </xsl:if>
        
        <!--Driving licence-->
        <xsl:if test="driving-licence/class">
            \NewPart{<xsl:value-of select="$texts//language[@id = $cv-language]/driving-licence" />}{}
            <xsl:apply-templates select="driving-licence/class"/>
        </xsl:if>
        
        <!--Characteristics-->
        <xsl:if test="characteristic/text() != ''">
            \NewPart{<xsl:value-of select="$texts//language[@id = $cv-language]/characteristics" />}{}
            <xsl:apply-templates select="characteristic" />
        </xsl:if>
        
        <!--Hobbies-->
        <xsl:if test="hobbies/text() != ''">
            \NewPart{<xsl:value-of select="$texts//language[@id = $cv-language]/hobbies" />}{}
            <xsl:apply-templates select="hobbies" />
        </xsl:if>
        \end{document}
    </xsl:template>
    
    <!-- Personal details -->
    <xsl:template match="/curriculum-vitae/personal-details">
        \PersonalEntry{<xsl:value-of select="$texts//language[@id = $cv-language]/name" />}{<xsl:value-of select="given-names" />}
        \PersonalEntry{<xsl:value-of select="$texts//language[@id = $cv-language]/surname" />}{<xsl:value-of select="surname" />}
        
        <!--Date of birth-->
        <xsl:apply-templates select="date-of-birth" />
        
        <!--Address-->
        <xsl:apply-templates select="address" />
        
        <!-- test if phones are present -->
        <xsl:if test="phones">
            <xsl:apply-templates select="phones" />
        </xsl:if>
        
        <!-- emails -->
        <xsl:if test="emails">
            <xsl:apply-templates select="emails" />
        </xsl:if>
        
        <!--social-->
        <xsl:if test="social">
            <xsl:apply-templates select="social" />
        </xsl:if>
    </xsl:template>
    
    <!-- date of birth -->
    <xsl:template match="/curriculum-vitae/personal-details/date-of-birth">
        \PersonalEntry{<xsl:value-of select="$texts//language[@id = $cv-language]/date-of-birth" />}{<xsl:value-of select="day" />. <xsl:value-of select="month" />. <xsl:value-of select="year" />}
    </xsl:template>
    
    <!-- address -->
    <xsl:template match="/curriculum-vitae/personal-details/address" >
        \PersonalEntry{<xsl:value-of select="$texts//language[@id = $cv-language]/address" />}{<xsl:value-of select="street" />
            <xsl:text>, </xsl:text>
            <xsl:value-of select="number" />
            <xsl:text> </xsl:text>
            <xsl:value-of select="city" />
        }
        \PersonalEntry{<xsl:value-of select="$texts//language[@id = $cv-language]/postal-code" />}{<xsl:value-of select="postal-code" />}
        
        <!--test if state is present-->
        <xsl:if test="state/text() !=''">
            \PersonalEntry{<xsl:value-of select="$texts//language[@id = $cv-language]/state" />}{<xsl:value-of select="state" />}
        </xsl:if>
        
        \PersonalEntry{<xsl:value-of select="$texts//language[@id = $cv-language]/country" />}{<xsl:value-of select="country" />}
    </xsl:template>
    
    <!-- phones -->
    <xsl:template match="/curriculum-vitae/personal-details/phones" >
        <xsl:variable name="count" select="count(phone)" />
        
        <!-- if only one phone is given, the title is Phone, otherwise Phones-->
        <xsl:if test="$count = '1'">
            \PersonalEntry{<xsl:value-of select="$texts//language[@id = $cv-language]/phone" />}{<xsl:value-of select="phone" />}
        </xsl:if>
        
        <xsl:if test="$count != '1'">
            <xsl:for-each select="phone" >
                
                <!--Use the same command but title is printed only once-->
                <xsl:variable name="i" select="position()" />
                <xsl:choose>
                    <xsl:when test="$i = '1'">
                        \PersonalEntry{<xsl:value-of select="$texts//language[@id = $cv-language]/phones" />}{<xsl:value-of select="." />}
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
            \PersonalEntry{<xsl:value-of select="$texts//language[@id = $cv-language]/email" />}{<xsl:value-of select="email" />}
        </xsl:if>
        
        <xsl:if test="$count != '1'">
            <xsl:for-each select="email" >
                <xsl:variable name="i" select="position()" />
                <xsl:choose>
                    <xsl:when test="$i = '1'">
                        \PersonalEntry{<xsl:value-of select="$texts//language[@id = $cv-language]/emails" />}{<xsl:value-of select="." />}
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
            \PersonalEntry{<xsl:value-of select="$texts//language[@id = $cv-language]/my-site" />}{<xsl:value-of select="site" />}
        </xsl:if>
        
        <xsl:if test="$count != '1'">
            <xsl:for-each select="site" >
                <xsl:variable name="i" select="position()" />
                <xsl:choose>
                    <xsl:when test="$i = '1'">
                        \PersonalEntry{<xsl:value-of select="$texts//language[@id = $cv-language]/my-sites" />}{<xsl:value-of select="." />}
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
            \EducationEntry{<xsl:value-of select="name-of-education" />}{<xsl:value-of select="./@from" /> - <xsl:value-of select="$texts//language[@id = $cv-language]/present" />}{<xsl:value-of select="name-of-school" />}{<xsl:value-of select="note" />}
        </xsl:if>
        
        <xsl:if test="$to != ''">
            \EducationEntry{<xsl:value-of select="name-of-education" />}{<xsl:value-of select="./@from" /> - <xsl:value-of select="./@to" />}{<xsl:value-of select="name-of-school" />}{<xsl:value-of select="note" />}
        </xsl:if>
        \sepspace
    </xsl:template>
    
    <!--employment -->
    <xsl:template match="/curriculum-vitae/employment/emp">
        <xsl:variable name="to" select="./@to"/>
        <xsl:if test="$to = ''">
            \EducationEntry{<xsl:value-of select="position" />}{<xsl:value-of select="./@from" /> - <xsl:value-of select="$texts//language[@id = $cv-language]/present" />}{<xsl:value-of select="company" />}{<xsl:value-of select="note" />}
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
        \sepspace
    </xsl:template>
    
    <!--certificates-->
    <xsl:template match="/curriculum-vitae/certificates/cert">
        \EducationEntry{<xsl:value-of select="name" />}{<xsl:value-of select="./@year" />}{<xsl:value-of select="note" />}
        \sepspace
    </xsl:template>
    
    <!--computer skills-->
    <xsl:template match="/curriculum-vitae/computer-skills/skill">
        \SkillsEntry{<xsl:value-of select="name" />}{<xsl:value-of select="note" />}
        \sepspace
    </xsl:template>
    
    <!--driving licence-->
    <xsl:template match="/curriculum-vitae/driving-licence/class">
        \SkillsEntry{<xsl:value-of select="name" />}{<xsl:value-of select="note" />}
        \sepspace
    </xsl:template>
    
    <!--characteristic-->
    <xsl:template match="/curriculum-vitae/characteristic">
        \PlainText{<xsl:value-of select="." />}
    </xsl:template>
    
    <!--hobbies-->
    <xsl:template match="/curriculum-vitae/hobbies">
        \PlainText{<xsl:value-of select="." />}
    </xsl:template>
    
    <!-- This template defines visual properties of output TeX file -->
    <xsl:template name="tex-style">
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
        
        \settowidth{\spacebox}{888888888888888888}	
        
        \newcommand{\sepspace}{\vspace*{1em}}

        \newcommand{\MySlogan}[1]{
                        \Huge \usefont{OT1}{phv}{b}{n}
                        \begin{center}
                        #1
                        \end{center}
                        \par \normalsize \normalfont}

        \newcommand{\NewPart}[1]{\section*{#1}}

        \newcommand{\PersonalEntry}[2]{
                        \noindent\hangindent=2em\hangafter=0
                        \parbox{\spacebox}{        
                        \textbf{#1}}		       
                        \hspace{1.5em} #2 \par}    

        \newcommand{\SkillsEntry}[2]{      
                        \noindent\hangindent=2em\hangafter=0 
                        \parbox{\spacebox}{        
                        \textit{#1}}			   
                        \hspace{1.5em} #2 \par}   	

        \newcommand{\EducationEntry}[4]{
                        \noindent \textbf{#1} \hfill     
                        \colorbox{LightGray}{%
                                \parbox{8em}{%
                                \hfill\color{Black}#2}} \par  
                        \noindent \textit{#3} \par       
                        \noindent\hangindent=2em\hangafter=0 \small #4 
                        \normalsize \par}
        \newcommand{\PlainText}[1]{\noindent\hangindent=2em\hangafter=0\par #1}
    </xsl:template>
</xsl:stylesheet>
