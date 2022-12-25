;**
;**	Logics for room 395 -- Bambi's face
;**
;**	LSL3:  Passionate Patti in Pursuit of the Pulsating Pectorals
;**	       Copyright 1989
;**		by Al Lowe, Bill Skirvin, Carlos Escobar, and Roger Hardy
;**
;**	Last Update:	October 21, 1989
;**


(script#				395)

(include	"face.sh")

(public
	rm395				0
)


;(define	vBambiFace					395)


(synonyms
	(woman bambi )
)


(enum												;**	RoomScript state values
	intro
	demo1
	demo1a
	demo2
	demo2b
	demo3
	demo4
	helpHer
	sex
	doThis
	goDoIt
	goodbye
	outtaHere
)


(local
	talkCount
	[string 55]
)


(instance rm395 of Room
	(properties
		picture		395
	)

	(method (init)
		(super init)

		(self
			setRegions		regBigFace,
			setLocales		locGirls,
			setScript 		RoomScript
		)

		(Notify regBigFace EyeWest		111  47)
		(Notify regBigFace EyeEast		174  85)
		(Notify regBigFace EyelidWest	 98  60)
		(Notify regBigFace EyelidEast	165  96)
		(Notify regBigFace Nose			 93 104)
		(Notify regBigFace Mouth		 90 111)
	)

	(method (newRoom n)
		(super newRoom n)
		(self
			dispose			regBigFace,
		)
	)
)


(instance RoomScript of Script
	(method (doit)
		(super doit)
	)


	(method (handleEvent event &tmp theObj)
		(if (and (not (super handleEvent event))
					(not (event claimed))
					modelessDialog
					(== (event message) ENTER)
					(== (event type) keyDown))
			(event claimed TRUE)
			(cls)
			(self cue)
		)

		(if (or	(!= (event type) saidEvent) (event claimed)) (return))

		(cond
			((Said 'give>')
				(= theObj (inventory saidMe))
				(event claimed FALSE)
				(cond
					((Said '[/noword]')
						(Print "What did you want to give her?")
					)
					((not theObj)
						(Print "It's not yours to give.")
					)
					((not	(theObj ownedBy ego))
						(DontHave)
					)
					((== theObj (inventory at iKeycard))
						(PrintP "\"Geez, Larry,
							I have one of those! I DO work here, you know!\"")
						(React rFrown)
					)
					((== theObj (inventory at iSoap))
						(PrintP "\"What's that hole from, Larry?\"")
						(React rFrown)
					)
					((== theObj (inventory at iDivorce))
						(PrintL "\"Look, Bambi! I'm a single guy.\"")
						(PrintP "\"So?\"")
						(React rFrown 6)
					)
					((== theObj (inventory at iKnife))
						(PrintP "\"Hey! Put that thing away!!
							I have no use for that!\"")
						(React rFrown)
					)
					((== theObj (inventory at iOrchids))
						(PrintP "\"Those do grow here, don't they?
							But orchids won't help me with this video!\"")
						(React rFrown)
					)
					((== theObj (inventory at iTowel))
						(PrintP "\"I'm perfectly capable of bringing my 
							own towel to work, Larry!\"")
						(React rFrown)
					)
					(else
						(PrintP "\"Why, thank you, honey,\" says Bambi,
							\"but I don't need `things,' 
							I need help with this damn video project of mine!!\"")
						(React rFrown)
					)
				)
				(event claimed TRUE)
			)							  			
			
			((Said 'fuck')
				(Print "You wonder if you are fit enough to handle this one.")
				(PrintP "\"Larry! Shame on you!
					Your face is an open book!\"")
				(React rFrown 4)
			)

			((or	(Said 'help//video')
					(Said 'help/video'))
				(cond
					((not egoIsHunk)
						(PrintL "\"Would you like some help with your video project,
							Bambi?\"")
						(PrintP "\"Not from you, Mr. Husky!
							What could a tub-o like you know about fitness?\"")
						(React rFrown 22)
					)
					(else
						(self changeState helpHer)
					)
				)
			)

			((or	(Said 'help')
					(Said '/help')
					(Said '//help'))
				(if (Random 0 1)
					(Print "How would you help her?")
				else
					(Print "Help her with what?")
				)
			)

			((or	(Said 'ask//video')
					(Said 'ask/video'))
				(PrintL "\"How is your video doing?\"")
				(PrintP "\"Not well.
					With so many workout tapes in today's marketplace,
					I'm having trouble coming up with that certain
					little something that will make mine different,
					establishing my competitive edge!\"")
				(React rFrown 11)
			)

			((or	(Said 'workout, (work<out)')
					(Said '/workout, class, (work<out)')
					(Said '//workout, class, (work<out)'))
				(PrintL "\"Could I work out with you, Bambi?\"")
				(PrintP "\"Maybe after I finish this aerobics video project,
					Larry.\"")
				(React rFrown 4)
			)

			((or	(Said '/tan, tanning, booth')
					(Said '//tan, tanning, booth'))
				(PrintL "\"What's the story on that locked door out in the lobby,
					Bambi?\"")
				(PrintP "\"Oh, that old thing?
					Why, that old tanning booth hasn't worked since the late
					eighties!\"")
			)

			((or	(Said '/clerk')
					(Said '//clerk'))
				(PrintL "\"What's the story on that man at the front desk?\"")
				(PrintP "\"He's only there for appearances, Larry.\"")
				(React rOh 5)
			)

			((Said 'show/prick')
				(PrintL "\"Hey, Bambi! Take a look at this, baby!\"")
				(PrintP "\"War injury, Larry?\"")
				(React rSmile 20)
			)

			((Said 'make/joke')
				(PrintL "You attempt to make a lame joke.")
				(PrintL "(You succeed.)" LOW)
				(PrintP "\"Hey, Larry! This is what I think of that joke!\"")
				(React rSilly 22)
			)

			((or	(Said '(out<go), dance, show, date/')
					(Said 'take/girl/home')
					(Said 'ask, go//room, place, comic, club, date, walk, bar, show, dance')
					(Said 'ask, go/room, place, comic, club, date, walk, bar, show, dance'))
				(PrintL "\"Bambi, would you like to see the town with me?\"")
				(PrintP "\"I've seen this town, Larry and it ain't that much!\"")
				(React rFrown 11)
			)

			((Said '/equipment, camera, tv')
				(PrintL "\"Why all this video equipment for a simple aerobics
					room?\"")
				(PrintP "\"Oh, that's not for my classes.
					I'm working on a new videotape for 
					worldwide distribution.\"")
				(React rSmile 20)
			)

			((Said 'talk/girl')
				(cond
					((not egoIsHunk)
						(PrintL (Format @string 
							"\"Hello, beautiful,\" you say, %s" laffer))
						(PrintP "\"My, my! Your body is certainly ready for
							one of my workouts!\"")
					)
					((TstFlg needsShower)
						(Printf "\"Hello, beautiful,\" you say, %s" laffer)
						(PrintP "\"Phewww! 
							You smell like you've been working out
							and didn't bother to shower. 
							How crude!\"")
						(React rFrown 11)
					)
					((TstFlg needsSoap)
						(Printf "\"Hello, beautiful,\" you say, %s" laffer)
						(PrintP "\"Phewww! 
							You smell like you've been working out
							and showered without using soap. 
							How crude!\"")
						(React rFrown 11)
					)
					((TstFlg needsDeodorant)
						(Printf "\"Hello, beautiful,\" you say, %s" laffer)
						(PrintP "\"Phewww! 
							You smell like you've been working out, showered
							but didn't use a deodorant.
							How crude!\"")
						(React rFrown 11)
					)
					(else
						(React rSmile 20)
						(switch (++ talkCount)
							(1
								(PrintL "\"Why all the elaborate video equipment 
									for a simple aerobics class?\"")
								(PrintP "\"Oh, that's not for my classes.
									I'm working on a new videotape for 
									worldwide distribution.\"")
							)
							(2
								(PrintL "\"So? How's it going?\"")
								(PrintP "\"Not well.
									With so many workout tapes in today's marketplace,
									I'm having trouble coming up with that certain
									little something that will make mine different,
									establishing my competitive edge!\"")
							)
							(else
								(PrintL "\"Say, I used to be in marketing.
									Perhaps I could help you?\"")
								(PrintP "\"Gosh, Larry! That would be wonderful.
									If you could, I'd be eternally grateful!\"")
							)
						)
					)
				)
			)

			((or	(Said 'love/you')
					(Said '/love/you'))
				(PrintL "\"Baby, I thinks I loves ya!\"")
				(PrintP "\"I'm sure you would!\"")
				(React rLove 44)
			)

			((or	(Said 'stop/look')
					(Said 'look<stop')
					(Said 'look/room')
					(Said 'quit, leave, exit, quit, walk, go')
					(Said 'rise, (stand<up), (get<off, up)'))
				(self changeState goodbye)
			)

			((Said 'look>')
				(cond
					((Said '/leg')
						(PrintP "\"Oh, Larry!\" smiles Bambi.
							\"Stop looking at me like that!\"")
						(React rSexy 22)
					)

					((Said '/bracelet')
						(Print "Her pearly white wrist passes through
							an expensive golden bracelet.")
					)

					((Said '/ear')
						(Print "You predict there is little or nothing between
							Bambi's ears.")
					)

					((or	(Said '/video')
							(Said '//video'))
						(PrintL "\"Bambi, is there any way I could see what
							you've done so far?\"")
						(PrintP "\"Not really, Larry. 
							I haven't yet figured out the hook that will make my
							tape different from all the others on the market.\"")
					)

					((Said '/tit')
						(Print "\"My, what a tasty little pair of hooters!\"
							you think to yourself.")
						(PrintP "\"Oh, Larry!\" smiles Bambi.
							\"Stop looking at me there!\"")
						(React rOh 12)
					)

					((Said '[/room, woman]')
						(Print "You feel certain you could learn to fawn over
							Bambi.")
						(PrintP "\"Hello,\" says Bambi with her doe-eyed smile.")
						(React rSmile 33)
					)
				)
			)
		)
	)


	(method (changeState newState)
		(switch (= state newState)

			(intro
;**					(if demo
;**						(delay 2)
;**					)
			)

;**				(demo1
;**					(Print "\"Hello, sweet thing. My name is Larry; Larry Laffer!\""
;**						LOW
;**						#title {You say...}
;**						#at 150 150
;**						#width 160
;**						#dispose
;**						#time 5)
;**					(delay (+ 2 5))
;**				)
;**	
;**				(demo1a
;**					(Print "\"To hear what I have to say, Larry,
;**						you'll have to buy your own copy of this game!\"
;**						she replies."
;**						#at 150 150
;**						#width 160
;**						#title {Bambi replies...}
;**						#dispose
;**						#time 8)
;**					(delay (+ 2 8))
;**					(React rSmile 33)
;**				)
;**	
;**				(demo2
;**					(Print "Since this is Sierra's first game to let you switch
;**						roles during play, let's take a good look at
;**						Passionate Patti."
;**						#at -1 146
;**						#dispose
;**						#time 7)
;**					(delay (+ 2 7))
;**				)
;**	
;**				(demo2b
;**					(Print "As Patti, you'll see things quite differently..."
;**						#at -1 146
;**						#dispose
;**						#time 5)
;**					(delay (+ 2 5))
;**				)
;**	
;**				(demo3
;**					(cls)
;**					(curRoom newRoom 450)
;**				)
;**	
;**				(demo4
;**					(cls)
;**					(curRoom newRoom 450)
;**				)


			;**	Help her with her video...

			(helpHer
				(HandsOff)
				(PrintL "\"Bambi! I've got it! 
					I know just the angle that will sell that video of yours!\"")
				(PrintP "\"Oh, really? What could that be, Larry?\"")
				(React rSmile 22)
				(= cycles 22)
			)

			(sex
				(theGame changeScore 99)
				(PrintL "\"SEX!\"")
				(PrintP "\"Of course! Why didn't I think of that?
					If sex sells toothpaste,
					it surely should sell aerobics!\"")
				(React rSmile 22)
				(= cycles 22)
			)

			(doThis
				(PrintL "\"We'll be the first aerobics video to use sexual 
					positions for our exercises.
					Can you do it, Bambi?
					Can you make it interesting?\"")
				(PrintP "\"Interesting?! I should say! Watch this!!\"")
				(React rSmile 22)
				(= cycles 22)
				(music fade)
			)

			(goDoIt
				(= egoState DOING_IT)
				(music stop)
				(curRoom newRoom 390)
			)


			;**	We're outta here...

			(goodbye
				(PrintL "\"I can't wait to see you work out again, Bambi!\"")
				(PrintP "\"Come back when you feel helpful, Larry!\"")
				(React rSmile 33)
				(= cycles 22)
			)

			(outtaHere
				(curRoom newRoom 390)
			)
		)
	)
)
;**					(PrintL "\"\"")
;**					(PrintP "\"\"")

