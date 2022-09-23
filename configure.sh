#!/usr/bin/env bash

buildpackages() {
  SUFFIX_DEFAULT=suffix
  COMPANY_DEFAULT=company
  PRODUCT_DEFAULT=product
  COMPONENT_DEFAULT=component
  COMMON_NAME_DEFAULT="Company, Inc."

  echo "First we are going to derive the root package structure for this java project."
  echo "Remember to use valid package names in the following prompts."
  echo "Please use abbreviations where appropriate because package names are code."

  read -p "Please enter the company's common name. This will be used for the docker company label [${COMMON_NAME:-$COMMON_NAME_DEFAULT}]: " COMMON_NAME
  COMMON_NAME=${COMMON_NAME:-$COMMON_NAME_DEFAULT}
  read -p "Please enter the company domain suffix in lowercase. This will be part of the package name [${SUFFIX:-$SUFFIX_DEFAULT}]:  " SUFFIX
  SUFFIX=${SUFFIX:-$SUFFIX_DEFAULT}
  read -p "Please enter the company domain name without the suffix in lowercase. This will be part of the package name [${COMPANY:-$COMPANY_DEFAULT}]:  " COMPANY
  COMPANY=${COMPANY:-$COMPANY_DEFAULT}
  read -p "Please enter the abbreviated or short product name in lowercase. This will be part of the package name [${PRODUCT:-$PRODUCT_DEFAULT}]:  " PRODUCT
  PRODUCT=${PRODUCT:-$PRODUCT_DEFAULT}
  read -p "Please enter the abbreviated or short component name in lowercase. This will be part of the package name [${COMPONENT:-$COMPONENT_DEFAULT}]:  " COMPONENT
  COMPONENT=${COMPONENT:-$COMPONENT_DEFAULT}
  echo ""
  read -p "Please enter the group-id lower-kebab-case. This will be part of the gradle group [${GROUP_ID:-$SUFFIX.$COMPANY.$PRODUCT}]:  " GROUP_ID
  GROUP_ID=${GROUP_ID:-$SUFFIX.$COMPANY.$PRODUCT}
  read -p "Please enter the artifact-id lower-kebab-case. This will be the gradle rootProject.name and jar name and usually has the product name in it [${ARTIFACT_ID:-$COMPONENT}]:  " ARTIFACT_ID
  ARTIFACT_ID=${ARTIFACT_ID:-$COMPONENT}
  echo ""
  PACKAGE="$SUFFIX.$COMPANY.$PRODUCT.$COMPONENT"
  read -p "The root package name will be: [$PACKAGE] do you accept [y/n]?: " accept
  case "$accept" in
    y|Y)
      return 0
      ;;
    *)
      echo
      echo "Okay, no problem. Let's start over."
      echo
      return 1
      ;;
  esac
}

renamepackages() {
  C_COMPONENT=$(echo $COMPONENT | awk '{print toupper(substr($0,0,1))tolower(substr($0,2))}')
  sed -i '' "s/ComponentApplication/${C_COMPONENT}Application/" src/main/java/suffix/company/product/component/ComponentApplication.java
  git mv src/main/java/suffix/company/product/component/ComponentApplication.java src/main/java/suffix/company/product/component/${C_COMPONENT}Application.java
  sed -i '' "s/ComponentApplicationTests/${C_COMPONENT}ApplicationTests/" src/test/java/suffix/company/product/component/ComponentApplicationTests.java
  git mv src/test/java/suffix/company/product/component/ComponentApplicationTests.java src/test/java/suffix/company/product/component/${C_COMPONENT}ApplicationTests.java
  echo "moving packages"
  git mv src/main/java/suffix/company/product/component src/main/java/suffix/company/product/$COMPONENT
  git mv src/test/java/suffix/company/product/component src/test/java/suffix/company/product/$COMPONENT
  git mv src/main/java/suffix/company/product src/main/java/suffix/company/$PRODUCT
  git mv src/test/java/suffix/company/product src/test/java/suffix/company/$PRODUCT
  git mv src/main/java/suffix/company src/main/java/suffix/$COMPANY
  git mv src/test/java/suffix/company src/test/java/suffix/$COMPANY
  git mv src/main/java/suffix src/main/java/$SUFFIX
  git mv src/test/java/suffix src/test/java/$SUFFIX
  git mv src/main/docker-compose/envs/component.env src/main/docker-compose/envs/${ARTIFACT_ID}.env
  echo "pruning empty directories."
  find src -empty -type d -delete
  echo "fixing packages."
  find src -name "*.java" -exec sed -i '' "s/package suffix\.company\.product\.component/package $PACKAGE/" {} +
  echo "fixing imports."
  find src -name "*.java" -exec sed -i '' "s/import suffix\.company\.product\.component/import $PACKAGE/" {} +
  echo "fixing build.gradle"
  sed -i '' "s/group = \'suffix\.company\.product\'/group = \'$GROUP_ID\'/" build.gradle
  sed -i '' "s/archiveFileName = \'component.jar\'/archiveFileName = \'$ARTIFACT_ID\.jar\'/" build.gradle
  echo "fixing settings.gradle"
  sed -i '' "s/component/$ARTIFACT_ID/" settings.gradle
  echo "fixing src/main/resources/application.yaml"
  sed -i '' "s/spring\.application\.name\: component/spring\.application\.name\: $COMPONENT/" src/main/resources/application.yaml
  sed -i '' "s/spring\.datasource\.username: component/spring\.datasource\.username: $COMPONENT/" src/main/resources/application.yaml
  sed -i '' "s/spring\.datasource\.password\: component/spring\.datasource\.password\: $COMPONENT/" src/main/resources/application.yaml
  echo "fixing docker-compose.yaml"
  sed -i '' "s/\component\.product\.company\.suffix/$COMPONENT\.$PRODUCT\.$COMPANY\.$SUFFIX/" docker-compose.yaml
  sed -i '' "s/image\: company\.suffix\/product\/component\:latest/image\: $COMPANY\.$SUFFIX\/$PRODUCT\/$COMPONENT\:latest/" docker-compose.yaml
  # \- src\/main\/docker\-compose\/envs\/component\.env
  sed -i '' "s/src\/main\/docker\-compose\/envs\/component\.env/src\/main\/docker\-compose\/envs\/$COMPONENT\.env/" docker-compose.yaml
  echo "fixing src/main/docker-compose/envs/${ARTIFACT_ID}.env"
  sed -i '' "s/postgresql\.db\.component\.product\.company\.suffix\:5432\/component/postgresql\.db\.$COMPONENT\.$PRODUCT\.$COMPANY\.$SUFFIX\:5432\/$COMPONENT/" src/main/docker-compose/envs/${ARTIFACT_ID}.env
  echo "fixing src/main/docker-compose/envs/db.env"
  sed -i '' "s/component/$COMPONENT/" src/main/docker-compose/envs/db.env
  echo "fixing src/main/docker-compose/schemaspy/schemaspy.properties"
  sed -i '' "s/postgresql\.db\.component\.product\.company\.suffix/postgresql\.db\.$COMPONENT\.$PRODUCT\.$COMPANY\.$SUFFIX/" src/main/docker-compose/schemaspy/schemaspy.properties
  sed -i '' "s/component/$COMPONENT/" src/main/docker-compose/schemaspy/schemaspy.properties
  echo "fixing run.sh"
  sed -i '' "s/component/$ARTIFACT_ID/" run.sh
  echo "fixing Dockerfile"
  sed -i '' "s/company\.suffix\/product\/component/$COMPANY\.$SUFFIX\/$PRODUCT\/$COMPONENT/g" Dockerfile
  sed -i '' "s/LABEL vendor=\"Company\"/LABEL vendor=\"$COMMON_NAME\"/" Dockerfile
  sed -i '' "s/LABEL app=\"component\"/LABEL app=\"$ARTIFACT_ID\"/" Dockerfile
  sed -i '' "s/company\/product\/component/$COMPANY\/$PRODUCT\/$COMPONENT/" Dockerfile
  sed -i '' "s/component\.jar/$ARTIFACT_ID\.jar/" Dockerfile

  # sed -i '' "s/pattern/substitution/" file
}

until buildpackages; do : ; done

renamepackages;







