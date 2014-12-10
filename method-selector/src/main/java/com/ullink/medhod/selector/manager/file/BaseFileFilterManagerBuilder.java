package com.ullink.medhod.selector.manager.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import com.ullink.medhod.selector.manager.DefaultMethodFilterManager;
import com.ullink.medhod.selector.manager.MethodFilterManager;
import com.ullink.medhod.selector.manager.file.reader.FileReader;
import com.ullink.medhod.selector.model.FilterEntry;

abstract class BaseFileFilterManagerBuilder
{
    private final File       file;
    private final FileReader reader;

    public final MethodFilterManager build() throws FileNotFoundException, IOException
    {
        Collection<FilterEntry> entries = this.reader.readFromFile(file);
        return new DefaultMethodFilterManager(entries);
    }

    protected BaseFileFilterManagerBuilder(File file, final FileReader reader)
    {
        super();
        this.file = file;
        this.reader = reader;
    }

}
