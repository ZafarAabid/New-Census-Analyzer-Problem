package censusanalyser;

public class CvsLoaderFactory {
    enum StateType {
        INT {
            public CensusAdaptor create(String asdf) {
                return new IndiaCensusAdaptor().loadCensusData() {
                };
            }
        },
        LOOKUPVALUE {
            public CensusAdaptor create(String asdf) {
                return new LookupValueValidator();
            }
        },
        DATE {
            public CensusAdaptor create(String asdf) {
                return new DateValidator();
            }
        };
        public CensusAdaptor create(String asdf) {
            return null;
        }
    }

    public static CensusAdaptor newInstance(StateType validatorType,String ... filePath) {
        return validatorType.create("asdf");
    }


}
