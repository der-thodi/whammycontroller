CXXFLAGS=-O -Wall -Wextra -Wpedantic -std=c++11
CPPFLAGS=-I$(INCDIR)

SRCDIR=src
OBJDIR=obj
INCDIR=include

SRCS=$(wildcard $(SRCDIR)/*.cpp)
OBJS=$(subst .cpp,.o,$(subst $(SRCDIR)/,$(OBJDIR)/,$(SRCS)))

EXE=whammycontroller

.PHONY:
	mkdir -p $(OBJDIR)

all: $(EXE)

whammycontroller: $(OBJS)
	$(CXX) $(CXXFLAGS) -o $(EXE) $(OBJS)

$(OBJDIR)/%.o: $(SRCDIR)/%.cpp
	$(CXX) $(CXXFLAGS) $(CPPFLAGS) -c $< -o $@

depend: .depend

.depend: $(SRCS)
	$(RM) ./.depend
	$(CXX) $(CPPFLAGS) -MM $^>>./.depend

clean:
	$(RM) $(OBJS) $(EXE)

distclean: clean
	$(RM) *~ .depend

include .depend
