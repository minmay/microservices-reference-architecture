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
  git mv src/main/java/suffix/company/product/component src/main/java/suffix/company/product/${COMPONENT}
  git mv src/test/java/suffix/company/product/component src/test/java/suffix/company/product/${COMPONENT}
  git mv src/main/java/suffix/company/product src/main/java/suffix/company/${PRODUCT}
  git mv src/test/java/suffix/company/product src/test/java/suffix/company/${PRODUCT}
  git mv src/main/java/suffix/company src/main/java/suffix/${COMPANY}
  git mv src/test/java/suffix/company src/test/java/suffix/${COMPANY}
  git mv src/main/java/suffix src/main/java/${SUFFIX}
  git mv src/test/java/suffix src/test/java/${SUFFIX}
  git mv conf/envs/component.env conf/envs/${ARTIFACT_ID}.env
  git mv conf/liquibase/component-liquibase.properties conf/liquibase/${COMPONENT}-liquibase.properties
  echo "pruning empty directories."
  find src -empty -type d -delete
  echo "fixing packages."
  find src -name "*.java" -exec sed -i '' "s/package suffix\.company\.product\.component/package $PACKAGE/" {} +
  echo "fixing imports."
  find src -name "*.java" -exec sed -i '' "s/import suffix\.company\.product\.component/import $PACKAGE/" {} +
  if [ "$SUFFIX" \< "org" ]; then
    printf '%s\n' '5m2' 'wq' | ed -s src/main/java/${SUFFIX}/${COMPANY}/${PRODUCT}/${COMPONENT}/api/repositories/OrganizationRepository.java
    printf '%s\n' '5m2' 'wq' | ed -s src/main/java/${SUFFIX}/${COMPANY}/${PRODUCT}/${COMPONENT}/api/repositories/OrganizationalUnitRepository.java
  fi
  echo "fixing build.gradle"
  sed -i '' "s/group = \'suffix\.company\.product\'/group = \'$GROUP_ID\'/" build.gradle
  sed -i '' "s/archiveFileName = \'component.jar\'/archiveFileName = \'$ARTIFACT_ID\.jar\'/" build.gradle
  echo "fixing settings.gradle"
  sed -i '' "s/component/$ARTIFACT_ID/" settings.gradle
  echo "fixing src/main/resources/application.yaml"
  sed -i '' "s/spring\.application\.name: component/spring\.application\.name: ${COMPONENT}/" src/main/resources/application.yaml
  sed -i '' "s/spring\.datasource\.username: product/spring\.datasource\.username: ${PRODUCT}/" src/main/resources/application.yaml
  sed -i '' "s/spring\.datasource\.password: product/spring\.datasource\.password: ${PRODUCT}/" src/main/resources/application.yaml
  sed -i '' "s/logging\.level\.suffix\.company/logging\.level\.${SUFFIX}\.${COMPANY}/" src/main/resources/application.yaml

  echo "fixing docker-compose.yaml"
  echo "docker-compose.yaml: change database name."
  sed -i '' "s/postgresql\.db\.product\.company\.suffix/postgresql\.db\.$PRODUCT\.$COMPANY\.$SUFFIX/" docker-compose.yaml
  echo "docker-compose.yaml: changed schema spy output."
  sed -i '' "s/command: SCHEMASPY\_OUTPUT=\/schemaspy\/component \/usr\/local\/bin\/schemaspy \-schemas public\,audit/command: SCHEMASPY\_OUTPUT=\/schemaspy\/$COMPONENT \/usr\/local\/bin\/schemaspy \-schemas public\,audit/" docker-compose.yaml
  echo "docker-compose.yaml: change ngnix volume"
  sed -i '' "s/\- schemaspy\.component\.product\.company\.suffix:\/usr\/share\/nginx\/html\/component:ro/\- schemaspy\.$COMPONENT\.$PRODUCT\.$COMPANY\.$SUFFIX:\/usr\/share\/nginx\/html\/$COMPONENT:ro/" docker-compose.yaml
  echo "docker-compose.yaml: change component name."
  sed -i '' "s/component\.product\.company\.suffix/$COMPONENT\.$PRODUCT\.$COMPANY\.$SUFFIX/" docker-compose.yaml
  echo "docker-compose.yaml: change component image name."
  sed -i '' "s/image: company\.suffix\/product\/component:latest/image: $COMPANY\.$SUFFIX\/$PRODUCT\/$COMPONENT:latest/" docker-compose.yaml
  sed -i '' "s/db\.product\.company\.suffix: {}/db\.$PRODUCT\.$COMPANY\.$SUFFIX: {}/" docker-compose.yaml

  echo "fixing docker-compose.override.yaml"
  echo "docker-compose.override.yaml: change database name."
  sed -i '' "s/postgresql\.db\.product\.company\.suffix/postgresql\.db\.$PRODUCT\.$COMPANY\.$SUFFIX/" docker-compose.override.yaml
  echo "docker-compose.override.yaml: change component name."
  sed -i '' "s/component\.product\.company\.suffix/$COMPONENT\.$PRODUCT\.$COMPANY\.$SUFFIX/" docker-compose.override.yaml
  echo "docker-compose.override.yaml: change component image name."
  sed -i '' "s/image: company\.suffix\/product\/component:latest/image: $COMPANY\.$SUFFIX\/$PRODUCT\/$COMPONENT:latest/" docker-compose.override.yaml
  sed -i '' "s/\- conf\/envs\/component\.env/\- conf\/envs\/$ARTIFACT_ID\.env/" docker-compose.override.yaml
  sed -i '' "s/\- \.\/conf\/liquibase\/component\-liquibase\.properties:\/liquibase\/config\/liquibase\.properties/\- \.\/conf\/liquibase\/$COMPONENT\-liquibase\.properties:\/liquibase\/config\/liquibase\.properties/" docker-compose.override.yaml

  # \- conf\/envs\/component\.env
  sed -i '' "s/conf\/envs\/component\.env/conf\/envs\/$COMPONENT\.env/" docker-compose.yaml
  echo "fixing conf/envs/${ARTIFACT_ID}.env"
  sed -i '' "s/postgresql\.db\.product\.company\.suffix:5432\/component/postgresql\.db\.$PRODUCT\.$COMPANY\.$SUFFIX:5432\/$COMPONENT/" conf/envs/${ARTIFACT_ID}.env
  echo "fixing conf/envs/db.env"
  sed -i '' "s/product/$PRODUCT/" conf/envs/db.env
  sed -i '' "s/component/$COMPONENT/" conf/envs/db.env
  echo "fixing conf/schemaspy/schemaspy.properties"
  sed -i '' "s/postgresql\.db\.product\.company\.suffix/postgresql\.db\.$PRODUCT\.$COMPANY\.$SUFFIX/" conf/schemaspy/schemaspy.properties
  sed -i '' "s/product/$PRODUCT/" conf/schemaspy/schemaspy.properties
  sed -i '' "s/component/$COMPONENT/" conf/schemaspy/schemaspy.properties
  echo "fixing conf/liquibase/component-liquibase.properties"
  sed -i '' "s/url\=jdbc:postgresql:\/\/postgresql\.db\.product\.company\.suffix:5432\/component/url\=jdbc:postgresql:\/\/postgresql\.db\.$PRODUCT\.$COMPANY\.$SUFFIX:5432\/$COMPONENT/" conf/liquibase/$COMPONENT-liquibase.properties
  sed -i '' "s/product/$PRODUCT/" conf/liquibase/$COMPONENT-liquibase.properties
  echo "fixing run.sh"
  sed -i '' "s/component/$ARTIFACT_ID/" run.sh
  echo "fixing Dockerfile"
  sed -i '' "s/company\.suffix\/product\/component/$COMPANY\.$SUFFIX\/$PRODUCT\/$COMPONENT/g" Dockerfile
  sed -i '' "s/LABEL vendor=\"Company\"/LABEL vendor=\"$COMMON_NAME\"/" Dockerfile
  sed -i '' "s/LABEL app=\"component\"/LABEL app=\"$ARTIFACT_ID\"/" Dockerfile
  sed -i '' "s/company\/product\/component/$COMPANY\/$PRODUCT\/$COMPONENT/" Dockerfile
  sed -i '' "s/component\.jar/$ARTIFACT_ID\.jar/" Dockerfile
  echo "fixing conf/nginx/nginx.conf"
  sed -i '' "s/component/$COMPONENT/" conf/nginx/nginx.conf
  # sed -i '' "s/pattern/substitution/" file
}

until buildpackages; do : ; done

renamepackages;
