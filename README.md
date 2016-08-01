# CopyExternalDB

  A simple copying external database library for Android
  
  [![](https://img.shields.io/badge/platform-android-green.svg)]()
  
## Download
In your ``build.gradle`` file


## Usage 

#### Step 1
Put your extarnal database in ``app/src/main/assets`` folder


#### Step 2 
Create your own ``DatabaseHelper`` like below
    
```java
public class DatabaseHelper extends CopyDatabase {
    
    // external db table name
    public static final String TABLE_NAME = "YOUR_DB_TABLE_NAME";
    
    // your application context
    public final Context mContext;
    
    // constructor
    public DatabaseHelper(Context context, int version, String databaseName) {
        super(context, version, databaseName);
        mContext = context;
    }

    // your external db columns here
    public static class Columns implements BaseColumns{

        public static String COLUMN_ONE = "YOUR_COLUMN";
        ...
        
        }
}
```

#### Step 3 
Create and open database in your activity like below
  
```java
DatabaseHelper databaseHelper = new DatabaseHelper(Context,DatabaseVersion,"YOUR_DB_NAME");
    
// extarnal database copy process
databaseHelper.createDatabase();

// extarnal database open process
databaseHelper.openDatabase();

```


**If you want to examine demo app** [click here](https://github.com/emrekose26/CopyExternalDB/tree/master/app/src/main/java/com/emrekose/copyexternaldbapp)

## License

    Copyright 2016 Emre Köse

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

